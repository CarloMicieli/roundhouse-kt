/*
 *   Copyright (c) 2021-2022 (C) Carlo Micieli
 *
 *    Licensed to the Apache Software Foundation (ASF) under one
 *    or more contributor license agreements.  See the NOTICE file
 *    distributed with this work for additional information
 *    regarding copyright ownership.  The ASF licenses this file
 *    to you under the Apache License, Version 2.0 (the
 *    "License"); you may not use this file except in compliance
 *    with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing,
 *    software distributed under the License is distributed on an
 *    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *    KIND, either express or implied.  See the License for the
 *    specific language governing permissions and limitations
 *    under the License.
 */
package io.github.carlomicieli.roundhouse.catalog.scales.createscales

import io.github.carlomicieli.roundhouse.catalog.scales.ScaleId
import io.github.carlomicieli.roundhouse.usecases.UseCaseResult
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.math.BigDecimal

@DisplayName("CreateScaleUseCase")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class CreateScaleUseCaseTest {
    lateinit var useCase: CreateScaleUseCase
    lateinit var createScaleRepository: CreateScaleRepository

    @BeforeAll
    fun setup() {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator

        createScaleRepository = mock()

        useCase = CreateScaleUseCase(validator, createScaleRepository)
    }

    @Test
    fun `should validate the input`() = runTest {
        val input = CreateScale(gauge = CreateScaleGauge())
        val result = useCase.execute(input)

        result.isError() shouldBe true
        val error = result.extractError()
        (error is CreateScaleError.InvalidRequest) shouldBe true

        val invalidRequest = error as CreateScaleError.InvalidRequest
        invalidRequest.errors shouldHaveSize 4
    }

    @Test
    fun `should check if the scale already exists`() = runTest {
        whenever(createScaleRepository.exists("H0")).doSuspendableAnswer { true }

        val input = CreateScale(
            name = "H0",
            ratio = BigDecimal.valueOf(87),
            gauge = CreateScaleGauge(trackGauge = "STANDARD")
        )
        val result = useCase.execute(input)

        result.isError() shouldBe true
        val error = result.extractError()
        (error is CreateScaleError.ScaleAlreadyExists) shouldBe true
    }

    @Test
    fun `should create new scale`() = runTest {
        whenever(createScaleRepository.exists("H0")).doSuspendableAnswer { false }

        val input = CreateScale(
            name = "H0",
            gauge = CreateScaleGauge(trackGauge = "STANDARD", millimeters = BigDecimal.valueOf(16.5), inches = BigDecimal.valueOf(0.65)),
            description = "Description goes here",
            ratio = BigDecimal.valueOf(87),
            standards = setOf("NEM")
        )
        val result = useCase.execute(input)

        result.isError() shouldBe false
        val output = result.extractOutput()
        output.id shouldBe ScaleId.of("H0")
    }

    private fun UseCaseResult<ScaleCreated, CreateScaleError>.extractOutput(): ScaleCreated = when (this) {
        is UseCaseResult.Output -> this.value
        else -> throw AssertionError("this result is not a use case output")
    }

    private fun UseCaseResult<ScaleCreated, CreateScaleError>.extractError(): CreateScaleError = when (this) {
        is UseCaseResult.Error -> this.value
        else -> throw AssertionError("this result is not an error")
    }
}
