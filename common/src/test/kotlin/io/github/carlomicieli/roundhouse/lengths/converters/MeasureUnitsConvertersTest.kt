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
package io.github.carlomicieli.roundhouse.lengths.converters

import io.github.carlomicieli.roundhouse.lengths.MeasureUnit
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@DisplayName("Measure unit conversion")
class MeasureUnitsConvertersTest {
    @Test
    fun `should throw a UnsupportedOperationException when the conversion is not valid`() {
        val converter = InvalidConverter(MeasureUnit.INCHES, MeasureUnit.KILOMETERS)
        val ex =
            shouldThrowExactly<UnsupportedOperationException> {
                converter.convert(BigDecimal.valueOf(10))
            }
        ex.message shouldBe "Unable to find a suitable converter from INCHES to KILOMETERS"
    }

    @Test
    fun `should return the same value for a same measure unit converter`() {
        val converter = SameUnitConverter
        val expected = BigDecimal.TEN
        val actual: BigDecimal = converter.convert(expected)

        actual shouldBe expected
    }

    @Test
    fun `should convert using a measure unit conversion rate`() {
        val converter = MeasureUnitConverterByRate(ConversionRate.INCHES_TO_MILLIMETERS)
        val result = converter.convert(BigDecimal.ONE, 1)
        result shouldBe BigDecimal.valueOf(25.4)
    }

    @Test
    fun `should convert values with decimals using a measure unit conversion rate`() {
        val converter = MeasureUnitConverterByRate(ConversionRate.INCHES_TO_MILLIMETERS)
        val result = converter.convert(BigDecimal.valueOf(0.65), 1)
        result shouldBe BigDecimal.valueOf(16.5)
    }
}
