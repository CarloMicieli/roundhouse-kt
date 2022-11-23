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
package com.trenako.infrastructure.persistence.catalog

import com.trenako.catalog.scales.Gauge
import com.trenako.catalog.scales.Ratio
import com.trenako.catalog.scales.ScaleId
import com.trenako.catalog.scales.ScaleView
import com.trenako.catalog.scales.Standard
import com.trenako.catalog.scales.TrackGauge
import com.trenako.lengths.Length
import com.trenako.metadata.MetadataInfo
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.Instant

@Table("scales")
data class ScaleRow(
    @Id
    val scaleId: ScaleId,
    val name: String,
    val ratio: BigDecimal,
    val gaugeMillimeters: BigDecimal,
    val gaugeInches: BigDecimal,
    val trackGauge: TrackGauge,
    val description: String?,
    val standards: String?,
    val version: Int,
    val created: Instant,
    val lastModified: Instant? = null
)

fun ScaleRow.toView(): ScaleView = ScaleView(
    id = this.scaleId,
    name = this.name,
    description = this.description,
    gauge = Gauge(
        millimetres = Length.ofMillimeters(this.gaugeMillimeters),
        inches = Length.ofInches(this.gaugeInches),
        trackGauge = trackGauge
    ),
    ratio = Ratio.of(this.ratio.toDouble()),
    standards = this.standards?.splitToSequence(',')?.map { Standard.valueOf(it) }?.toSet() ?: setOf(),
    metadata = MetadataInfo(
        version = this.version,
        createdAt = this.created,
        lastModified = this.lastModified
    )
)
