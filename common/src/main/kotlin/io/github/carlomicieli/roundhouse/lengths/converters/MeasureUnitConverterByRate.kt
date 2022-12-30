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

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * A measure unit converter which is using the provided rate to perform the conversion.
 * @param rate the conversion rate
 */
class MeasureUnitConverterByRate(private val rate: BigDecimal) : MeasureUnitConverter {
    init {
        require(rate.signum() > 0) { "conversion rate must be positive" }
    }

    override fun convert(value: BigDecimal, decimals: Int): BigDecimal =
        (value * rate).setScale(decimals, RoundingMode.HALF_UP)
}
