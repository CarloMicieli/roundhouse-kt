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
package io.github.carlomicieli.roundhouse.catalog.brands.validation.constraints

import io.github.carlomicieli.roundhouse.catalog.brands.BrandKind
import io.github.carlomicieli.roundhouse.catalog.brands.BrandStatus
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation
import jakarta.validation.Validator
import jakarta.validation.ValidatorFactory
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

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
    @DisplayName("kind")
    inner class KindValidator {
        @Test
        fun `should pass validation for null values`() {
            val input = BrandKindClass(kind = null)
            val errors = validator.validate(input)
            errors shouldHaveSize 0
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "INDUSTRIAL",
                "BRASS_MODELS"
            ]
        )
        fun `should pass validation for valid brand kind values`(kind: String) {
            val input = BrandKindClass(kind)
            val errors = validator.validate(input)
            errors shouldHaveSize 0
        }

        @Test
        fun `should pass validation for valid brand kind values, ignoring the case`() {
            val input = BrandKindClass(BrandKind.INDUSTRIAL.toString().lowercase())
            val errors = validator.validate(input)
            errors shouldHaveSize 0
        }

        @Test
        fun `should fail validation for invalid brand kind values`() {
            val input = BrandKindClass("NOT_A_VALID_NAME")

            val errors = validator.validate(input)

            errors shouldHaveSize 1
            val error = errors.first()
            error.message shouldBe "{io.github.carlomicieli.roundhouse.brand.kind.invalid}"
            error.invalidValue shouldBe "NOT_A_VALID_NAME"
            error.propertyPath.toString() shouldBe "kind"
        }
    }

    @Nested
    @DisplayName("status")
    inner class StatusValidator {
        @Test
        fun `should pass validation for null values`() {
            val input = BrandStatusClass(status = null)
            val errors = validator.validate(input)
            errors shouldHaveSize 0
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "ACTIVE",
                "OUT_OF_BUSINESS"
            ]
        )
        fun `should pass validation for valid brand status values`(status: String) {
            val input = BrandStatusClass(status)
            val errors = validator.validate(input)
            errors shouldHaveSize 0
        }

        @Test
        fun `should pass validation for valid brand status values, ignoring the case`() {
            val input = BrandStatusClass(BrandStatus.ACTIVE.toString().lowercase())
            val errors = validator.validate(input)
            errors shouldHaveSize 0
        }

        @Test
        fun `should fail validation for invalid brand status values`() {
            val input = BrandStatusClass("NOT_A_VALID_NAME")

            val errors = validator.validate(input)

            errors shouldHaveSize 1
            val error = errors.first()
            error.message shouldBe "{io.github.carlomicieli.roundhouse.brand.status.invalid}"
            error.invalidValue shouldBe "NOT_A_VALID_NAME"
            error.propertyPath.toString() shouldBe "status"
        }
    }

    data class BrandKindClass(@field:ValidBrandKind val kind: String?)
    data class BrandStatusClass(@field:ValidBrandStatus val status: String?)
}
