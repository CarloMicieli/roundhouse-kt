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
import io.github.carlomicieli.roundhouse.lengths.converters.ConversionRate.INCHES_TO_MILLIMETERS
import io.github.carlomicieli.roundhouse.lengths.converters.ConversionRate.KILOMETERS_TO_MILES
import io.github.carlomicieli.roundhouse.lengths.converters.ConversionRate.MILES_TO_KILOMETERS
import io.github.carlomicieli.roundhouse.lengths.converters.ConversionRate.MILLIMETERS_TO_INCHES
import java.math.BigDecimal

typealias Conversion = Pair<MeasureUnit, MeasureUnit>

object MeasureUnitsConverters {
    private val conversions: Map<Conversion, MeasureUnitConverter> = mapOf(
        (MeasureUnit.INCHES to MeasureUnit.MILLIMETERS) to MeasureUnitConverterByRate(INCHES_TO_MILLIMETERS),
        (MeasureUnit.MILLIMETERS to MeasureUnit.INCHES) to MeasureUnitConverterByRate(MILLIMETERS_TO_INCHES),
        (MeasureUnit.KILOMETERS to MeasureUnit.MILES) to MeasureUnitConverterByRate(KILOMETERS_TO_MILES),
        (MeasureUnit.MILES to MeasureUnit.KILOMETERS) to MeasureUnitConverterByRate(MILES_TO_KILOMETERS)
    )

    operator fun get(conversion: Conversion): MeasureUnitConverter =
        conversions[conversion] ?: InvalidConverter(conversion.first, conversion.second)
}

object ConversionRate {
    val INCHES_TO_MILLIMETERS: BigDecimal = BigDecimal.valueOf(25.4)
    val MILLIMETERS_TO_INCHES: BigDecimal = BigDecimal.valueOf(0.0393701)
    val MILES_TO_KILOMETERS: BigDecimal = BigDecimal.valueOf(1.60934)
    val KILOMETERS_TO_MILES: BigDecimal = BigDecimal.valueOf(0.621371)
}
