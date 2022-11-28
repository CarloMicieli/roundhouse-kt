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
package com.trenako.infrastructure.persistence.catalog.seeding

import com.trenako.catalog.scales.Gauge
import com.trenako.catalog.scales.Ratio
import com.trenako.catalog.scales.ScaleId
import com.trenako.catalog.scales.Standard
import com.trenako.catalog.scales.TrackGauge
import com.trenako.catalog.scales.createscales.CreateScaleRepository
import com.trenako.catalog.scales.createscales.NewScale
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal

class ScalesSeeding(private val createScaleRepository: CreateScaleRepository) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(ScalesSeeding::class.java)
    }

    suspend fun seed() {
        insert(H0())
        insert(TT())
        insert(N())
        insert(Z())
    }

    private suspend fun insert(b: NewScale) {
        createScaleRepository.insert(b)
        log.info("Scale ${b.name} inserted.")
    }
}

@Suppress("FunctionName")
fun H0(): NewScale = NewScale(
    id = ScaleId.of("H0"),
    name = "H0",
    description = "",
    ratio = Ratio.of(87f),
    gauge = Gauge.ofMillimetres(BigDecimal.valueOf(16.5), TrackGauge.STANDARD),
    standards = setOf(Standard.NEM)
)

@Suppress("FunctionName")
fun TT(): NewScale = NewScale(
    id = ScaleId.of("TT"),
    name = "TT",
    description = "",
    ratio = Ratio.of(120f),
    gauge = Gauge.ofMillimetres(BigDecimal.valueOf(12.0), TrackGauge.STANDARD),
    standards = setOf(Standard.NEM)
)

@Suppress("FunctionName")
fun N(): NewScale = NewScale(
    id = ScaleId.of("N"),
    name = "N",
    description = "",
    ratio = Ratio.of(160f),
    gauge = Gauge.ofMillimetres(BigDecimal.valueOf(9.0), TrackGauge.STANDARD),
    standards = setOf(Standard.NEM)
)

@Suppress("FunctionName")
fun Z(): NewScale = NewScale(
    id = ScaleId.of("Z"),
    name = "Z",
    description = "",
    ratio = Ratio.of(220f),
    gauge = Gauge.ofMillimetres(BigDecimal.valueOf(6.5), TrackGauge.STANDARD),
    standards = setOf(Standard.NEM)
)
