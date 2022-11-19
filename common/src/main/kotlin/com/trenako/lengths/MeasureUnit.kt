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

import com.trenako.lengths.converters.MeasureUnitConverter
import com.trenako.lengths.converters.MeasureUnitsConverters
import java.math.BigDecimal

enum class MeasureUnit(private val symbol: String) {
    MILLIMETERS("mm"),
    INCHES("in"),
    MILES("mi"),
    KILOMETERS("km");

    internal fun buildString(value: BigDecimal?): String {
        return "$value $symbol"
    }

    /**
     * Returns the appropriate converter to convert from this MeasureUnit to the other If such
     * converter does not exist, a converter that always failed is returned instead.
     */
    fun convertTo(other: MeasureUnit): MeasureUnitConverter {
        return MeasureUnitsConverters[this to other]
    }
}
