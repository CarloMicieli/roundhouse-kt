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

import com.trenako.lengths.Length
import com.trenako.lengths.MeasureUnit
import com.trenako.lengths.converters.MeasureUnitConverter
import java.math.BigDecimal
import java.util.Objects

class Gauge(val millimetres: Length, val inches: Length, val trackGauge: TrackGauge) : Comparable<Gauge> {

    override fun compareTo(other: Gauge): Int = this.millimetres.compareTo(other.millimetres)

    override fun equals(other: Any?): Boolean = other is Gauge &&
        this.millimetres == other.millimetres &&
        this.inches == other.inches &&
        this.trackGauge == other.trackGauge

    override fun hashCode(): Int = Objects.hash(millimetres, inches, trackGauge)

    override fun toString(): String = "Gauge(millimetres = $millimetres, inches = $inches, track gauge = $trackGauge)"

    companion object {
        private val mmToInches: MeasureUnitConverter = MeasureUnit.MILLIMETERS.convertTo(MeasureUnit.INCHES)
        private val inchesToMm: MeasureUnitConverter = MeasureUnit.INCHES.convertTo(MeasureUnit.MILLIMETERS)

        fun ofMillimetres(millimetres: BigDecimal, trackGauge: TrackGauge = TrackGauge.STANDARD): Gauge {
            val inches = mmToInches.convert(millimetres)
            return Gauge(
                millimetres = Length.ofMillimeters(millimetres),
                inches = Length.ofInches(inches),
                trackGauge = trackGauge
            )
        }

        fun ofInches(inches: BigDecimal, trackGauge: TrackGauge = TrackGauge.STANDARD): Gauge {
            val millimetres = inchesToMm.convert(inches, 1)
            return Gauge(
                millimetres = Length.ofMillimeters(millimetres),
                inches = Length.ofInches(inches),
                trackGauge = trackGauge
            )
        }
    }
}
