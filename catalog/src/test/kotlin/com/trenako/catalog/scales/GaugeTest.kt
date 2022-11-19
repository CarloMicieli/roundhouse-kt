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
package com.trenako.catalog.scales

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

@DisplayName("Gauge")
class GaugeTest {

    @Test
    fun `should create new scale gauge from a millimetres value`() {
        val millimetres = BigDecimal.valueOf(16.5)
        val gauge = Gauge.ofMillimetres(millimetres, trackGauge = TrackGauge.NARROW)

        gauge.millimetres.toString() shouldBe "16.5 mm"
        gauge.inches.toString() shouldBe "0.65 in"
        gauge.trackGauge shouldBe TrackGauge.NARROW
    }

    @Test
    fun `should create new scale gauge from an inches value`() {
        val inches = BigDecimal.valueOf(0.65)
        val gauge = Gauge.ofInches(inches, trackGauge = TrackGauge.NARROW)

        gauge.millimetres.toString() shouldBe "16.5 mm"
        gauge.inches.toString() shouldBe "0.65 in"
        gauge.trackGauge shouldBe TrackGauge.NARROW
    }

    @Test
    fun `should produce string representations for gauges`() {
        val inches = BigDecimal.valueOf(0.65)
        val gauge = Gauge.ofInches(inches, trackGauge = TrackGauge.NARROW)
        gauge.toString() shouldBe "Gauge(millimetres = 16.5 mm, inches = 0.65 in, track gauge = NARROW)"
    }

    @Test
    fun `should check for gauge equality`() {
        val millimetres = BigDecimal.valueOf(16.5)
        val inches = BigDecimal.valueOf(0.65)
        val inches2 = BigDecimal.valueOf(10.23)

        val gauge1 = Gauge.ofMillimetres(millimetres, trackGauge = TrackGauge.NARROW)
        val gauge2 = Gauge.ofInches(inches, trackGauge = TrackGauge.NARROW)
        val gauge3 = Gauge.ofInches(inches2, trackGauge = TrackGauge.NARROW)
        val gauge4 = Gauge.ofInches(inches2, trackGauge = TrackGauge.STANDARD)

        (gauge1 == gauge2) shouldBe true
        (gauge1 != gauge2) shouldBe false
        (gauge1 == gauge3) shouldBe false
        (gauge1 != gauge3) shouldBe true
        (gauge3 == gauge4) shouldBe false
        (gauge3 != gauge4) shouldBe true
    }

    @Test
    fun `should calculate the hash code from a gauge`() {
        val millimetres = BigDecimal.valueOf(16.5)
        val inches = BigDecimal.valueOf(0.65)

        val gauge1 = Gauge.ofMillimetres(millimetres, trackGauge = TrackGauge.NARROW)
        val gauge2 = Gauge.ofInches(inches, trackGauge = TrackGauge.NARROW)

        gauge1.hashCode() shouldBe gauge2.hashCode()
    }

    @Test
    fun `should compare two gauges`() {
        val millimetres = BigDecimal.valueOf(16.5)
        val inches = BigDecimal.valueOf(0.99)

        val gauge1 = Gauge.ofMillimetres(millimetres, trackGauge = TrackGauge.NARROW)
        val gauge2 = Gauge.ofInches(inches, trackGauge = TrackGauge.NARROW)

        (gauge1 < gauge2) shouldBe true
        (gauge1 > gauge2) shouldBe false
    }
}
