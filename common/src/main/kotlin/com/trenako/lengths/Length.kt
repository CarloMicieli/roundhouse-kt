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
package com.trenako.lengths

import java.math.BigDecimal

/**
 * It represents a non-negative unit of length.
 */
data class Length(val value: BigDecimal, val measureUnit: MeasureUnit) : Comparable<Length> {
    init {
        require(value.signum() >= 0) { "a length value cannot be negative" }
    }

    override fun compareTo(other: Length): Int {
        val converted = other.convertTo(this.measureUnit)
        return this.value.compareTo(converted.value)
    }

    override fun toString(): String =
        measureUnit.buildString(value)

    companion object {
        fun ofKilometers(value: BigDecimal): Length =
            Length(value, MeasureUnit.KILOMETERS)

        fun ofMiles(value: BigDecimal): Length =
            Length(value, MeasureUnit.MILES)

        fun ofMillimeters(value: BigDecimal): Length =
            Length(value, MeasureUnit.MILLIMETERS)

        fun ofInches(value: BigDecimal): Length =
            Length(value, MeasureUnit.INCHES)

        fun valueOf(value: Double, measureUnit: MeasureUnit): Length =
            Length(BigDecimal.valueOf(value), measureUnit)

        fun valueOf(value: Long, measureUnit: MeasureUnit): Length =
            Length(BigDecimal.valueOf(value), measureUnit)

        /**
         * A constant length of zero millimeters
         */
        val ZeroMillimeters = Length(BigDecimal.ZERO, MeasureUnit.MILLIMETERS)

        /**
         * A constant length of zero inches
         */
        val ZeroInches = Length(BigDecimal.ZERO, MeasureUnit.INCHES)

        /**
         * A constant length of zero kilometers
         */
        val ZeroKilometers = Length(BigDecimal.ZERO, MeasureUnit.KILOMETERS)

        /**
         * A constant length of zero miles
         */
        val ZeroMiles = Length(BigDecimal.ZERO, MeasureUnit.MILES)
    }
}

fun Length.convertTo(otherMeasureUnit: MeasureUnit): Length {
    return if (this.measureUnit == otherMeasureUnit) {
        this
    } else {
        val converted = this.measureUnit.convertTo(otherMeasureUnit).convert(this.value)
        Length(converted, otherMeasureUnit)
    }
}
