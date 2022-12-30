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

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@DisplayName("MeasureUnit")
class MeasureUnitTest {
    @Test
    fun `should produce string representations for length in inches`() {
        MeasureUnit.INCHES.buildString(BigDecimal.valueOf(0.65)) shouldBe "0.65 in"
        MeasureUnit.INCHES.buildString(BigDecimal.valueOf(16.5)) shouldBe "16.5 in"
    }

    @Test
    fun `should produce string representations for length in millimeters`() {
        MeasureUnit.MILLIMETERS.buildString(BigDecimal.valueOf(0.65)) shouldBe "0.65 mm"
        MeasureUnit.MILLIMETERS.buildString(BigDecimal.valueOf(16.5)) shouldBe "16.5 mm"
    }

    @Test
    fun `should produce string representations for length in kilometers`() {
        MeasureUnit.KILOMETERS.buildString(BigDecimal.valueOf(0.65)) shouldBe "0.65 km"
        MeasureUnit.KILOMETERS.buildString(BigDecimal.valueOf(16.5)) shouldBe "16.5 km"
    }

    @Test
    fun `should produce string representations for length in miles`() {
        MeasureUnit.MILES.buildString(BigDecimal.valueOf(0.65)) shouldBe "0.65 mi"
        MeasureUnit.MILES.buildString(BigDecimal.valueOf(16.5)) shouldBe "16.5 mi"
    }
}
