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
package io.github.carlomicieli.roundhouse.catalog.brands.createbrands

import io.github.carlomicieli.roundhouse.catalog.brands.BrandId
import io.github.carlomicieli.roundhouse.catalog.brands.BrandKind
import io.github.carlomicieli.roundhouse.usecases.UseCaseResult
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

@DisplayName("Create brand use case")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class CreateBrandUseCaseTest {
    lateinit var useCase: CreateBrandUseCase
    lateinit var createBrandRepository: CreateBrandRepository

    @BeforeAll
    fun setup() {
        val factory = Validation.buildDefaultValidatorFactory()
        val validator = factory.validator

        createBrandRepository = mock()

        useCase = CreateBrandUseCase(validator, createBrandRepository)
    }

    @Test
    fun `should validate the input`() =
        runTest {
            val input = CreateBrand()
            val result = useCase.execute(input)

            result.isError() shouldBe true
            val error = result.extractError()
            (error is CreateBrandError.InvalidRequest) shouldBe true
        }

    @Test
    fun `should create new brands`() =
        runTest {
            whenever(createBrandRepository.exists("ACME")).doSuspendableAnswer { false }

            val input =
                CreateBrand(
                    name = "ACME",
                    registeredCompanyName = "ACME Srl",
                    groupName = "ACME",
                    description = "Description goes here",
                    kind = BrandKind.INDUSTRIAL.name,
                    contactInfo =
                        CreateBrandContactInfo(
                            email = "mail@mail.com",
                            websiteUrl = "https://www.website.com",
                            phoneNumber = "555 1234"
                        ),
                    status = "ACTIVE"
                )
            val result = useCase.execute(input)

            result.isError() shouldBe false
            val output = result.extractOutput()
            output.id shouldBe io.github.carlomicieli.roundhouse.catalog.brands.BrandId.of("ACME")
        }

    @Test
    fun `should check if the brand already exists`() =
        runTest {
            whenever(createBrandRepository.exists("ACME")).doSuspendableAnswer { true }

            val input =
                CreateBrand(
                    name = "ACME",
                    kind = BrandKind.INDUSTRIAL.name
                )
            val result = useCase.execute(input)

            result.isError() shouldBe true
            val error = result.extractError()
            (error is CreateBrandError.BrandAlreadyExists) shouldBe true
        }

    private fun UseCaseResult<BrandCreated, CreateBrandError>.extractError(): CreateBrandError =
        when (this) {
            is UseCaseResult.Error -> this.value
            else -> throw AssertionError("this result is not an error")
        }

    private fun UseCaseResult<BrandCreated, CreateBrandError>.extractOutput(): BrandCreated =
        when (this) {
            is UseCaseResult.Output -> this.value
            else -> throw AssertionError("this result is not a use case output")
        }
}
