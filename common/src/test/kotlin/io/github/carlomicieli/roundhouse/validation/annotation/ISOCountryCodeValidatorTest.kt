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
package io.github.carlomicieli.roundhouse.validation.annotation

import io.github.carlomicieli.roundhouse.validation.annotation.constraints.ISOCountryCode
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation
import jakarta.validation.Validator
import jakarta.validation.ValidatorFactory
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@DisplayName("ISOCountryCode validator")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ISOCountryCodeValidatorTest {
    private lateinit var factory: ValidatorFactory
    private lateinit var validator: Validator

    @BeforeAll
    fun setup() {
        factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @AfterAll
    fun cleanup() {
        factory.close()
    }

    @Test
    fun `should not validate null values`() {
        val value = TestClass()
        val errors = validator.validate(value)
        errors shouldHaveSize 0
    }

    @Test
    fun `should succeed to validate valid ISO country codes`() {
        val value = TestClass("de")

        val errors = validator.validate(value)

        errors shouldHaveSize 0
    }

    @Test
    fun `should fail to validate invalid country codes`() {
        val value = TestClass("yy")

        val errors = validator.validate(value)

        errors shouldHaveSize 1
        val firstError = errors.first()
        firstError.propertyPath.toString() shouldBe "countryCode"
        firstError.invalidValue shouldBe "yy"
        firstError.message shouldBe "{io.github.carlomicieli.roundhouse.country.code.invalid}"
    }

    data class TestClass(
        @field:ISOCountryCode
        val countryCode: String? = null
    )
}
