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
package com.trenako.catalog.brands.validation.constraints

import com.trenako.catalog.brands.BrandKind
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

@DisplayName("Brand validators")
@TestInstance(Lifecycle.PER_CLASS)
class BrandValidatorsTest {
    lateinit var factory: ValidatorFactory
    lateinit var validator: Validator

    @BeforeAll
    fun setup() {
        factory = Validation.buildDefaultValidatorFactory()
        validator = factory.validator
    }

    @AfterAll
    fun cleanup() {
        factory.close()
    }

    @Nested
    @DisplayName("BrandKind")
    inner class BrandKindValidator {
        @Test
        fun `should pass validation for null values`() {
            val input = MyClass(kind = null)
            val errors = validator.validate(input)
            errors shouldHaveSize 0
        }

        @Test
        fun `should pass validation for valid brand kind values`() {
            val input = MyClass(kind = BrandKind.INDUSTRIAL.toString())
            val errors = validator.validate(input)
            errors shouldHaveSize 0
        }

        @Test
        fun `should pass validation for valid brand kind values, ignoring the case`() {
            val input = MyClass(kind = BrandKind.INDUSTRIAL.toString().lowercase())
            val errors = validator.validate(input)
            errors shouldHaveSize 0
        }

        @Test
        fun `should fail validation for invalid brand kind values`() {
            val input = MyClass(kind = "NOT_A_VALID_NAME")

            val errors = validator.validate(input)

            errors shouldHaveSize 1
            val error = errors.first()
            error.message shouldBe "{com.trenako.brand.kind.invalid}"
            error.invalidValue shouldBe "NOT_A_VALID_NAME"
            error.propertyPath.toString() shouldBe "kind"
        }
    }

    data class MyClass(@field:ValidBrandKind val kind: String?)
}
