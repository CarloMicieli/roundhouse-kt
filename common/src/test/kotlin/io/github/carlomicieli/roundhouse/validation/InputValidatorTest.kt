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
package io.github.carlomicieli.roundhouse.validation

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

@DisplayName("UseCaseInputValidator")
@TestInstance(Lifecycle.PER_CLASS)
class InputValidatorTest {
    lateinit var factory: ValidatorFactory
    lateinit var validator: InputValidator<MyBean>

    @BeforeAll
    fun setup() {
        factory = Validation.buildDefaultValidatorFactory()
        validator = InputValidator(factory.validator)
    }

    @AfterAll
    fun cleanup() {
        factory.close()
    }

    @Test
    fun `should validate valid inputs`() {
        val valid = MyBean("Joe", 42)

        val result = validator.validate(valid)

        (result is Validated.Valid) shouldBe true
    }

    @Test
    fun `should fail to validate invalid inputs`() {
        val invalid = MyBean("", -1)

        val result = validator.validate(invalid)

        (result is Validated.Invalid) shouldBe true
        val invalidValidation = result as Validated.Invalid
        invalidValidation.errors shouldHaveSize 2
        invalidValidation.errors shouldContain ValidationError("fullName", "must not be empty", "")
        invalidValidation.errors shouldContain ValidationError("age", "must be greater than 0", -1)
    }
}

data class MyBean(
    @field:NotEmpty
    val fullName: String,

    @field:Positive
    val age: Int?
)
