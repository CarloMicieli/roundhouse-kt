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
package io.github.carlomicieli.roundhouse.lengths

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@DisplayName("Length")
class LengthTest {
    @Test
    fun `is created with the Km measure unit`() {
        val oneHundred = BigDecimal.valueOf(100)

        val km = Length.ofKilometers(oneHundred)

        km.value shouldBe oneHundred
        km.measureUnit shouldBe MeasureUnit.KILOMETERS
    }

    @Test
    fun `is created with the miles measure unit`() {
        val oneHundred = BigDecimal.valueOf(100)

        val miles = Length.ofMiles(oneHundred)

        miles.value shouldBe oneHundred
        miles.measureUnit shouldBe MeasureUnit.MILES
    }

    @Test
    fun `is created with the Millimeters measure unit`() {
        val oneHundred = BigDecimal.valueOf(100)

        val miles = Length.ofMillimeters(oneHundred)

        miles.value shouldBe oneHundred
        miles.measureUnit shouldBe MeasureUnit.MILLIMETERS
    }

    @Test
    fun `is created with the inches measure unit`() {
        val oneHundred = BigDecimal.valueOf(100)

        val inches = Length.ofInches(oneHundred)

        inches.value shouldBe oneHundred
        inches.measureUnit shouldBe MeasureUnit.INCHES
    }

    @Test
    fun `is created with a value and measure unit`() {
        val length = Length.valueOf(42, MeasureUnit.INCHES)
        length.value shouldBe BigDecimal.valueOf(42)
        length.measureUnit shouldBe MeasureUnit.INCHES
    }

    @Test
    fun `can only assume non negative values`() {
        val ex = shouldThrowExactly<IllegalArgumentException> {
            Length.valueOf(-1, MeasureUnit.KILOMETERS)
        }
        ex.message shouldBe "a length value cannot be negative"
    }

    @Test
    fun `defines zero length constants`() {
        Length.ZeroKilometers.value shouldBe BigDecimal.ZERO
        Length.ZeroKilometers.measureUnit shouldBe MeasureUnit.KILOMETERS

        Length.ZeroMiles.value shouldBe BigDecimal.ZERO
        Length.ZeroMiles.measureUnit shouldBe MeasureUnit.MILES

        Length.ZeroInches.value shouldBe BigDecimal.ZERO
        Length.ZeroInches.measureUnit shouldBe MeasureUnit.INCHES

        Length.ZeroMillimeters.value shouldBe BigDecimal.ZERO
        Length.ZeroMillimeters.measureUnit shouldBe MeasureUnit.MILLIMETERS
    }

    @Test
    fun `has a string representation`() {
        val len = Length.valueOf(42, MeasureUnit.INCHES)
        len.toString() shouldBe "42 in"
    }

    @Test
    fun `should compare two length values with different measure units`() {
        val value = BigDecimal.valueOf(100)
        val len1 = Length.ofInches(value)
        val len2 = Length.ofMillimeters(value)

        (len1 > len2) shouldBe true
        (len1 < len2) shouldBe false
    }
}
