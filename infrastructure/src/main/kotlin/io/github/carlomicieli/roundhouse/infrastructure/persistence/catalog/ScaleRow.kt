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
package io.github.carlomicieli.roundhouse.infrastructure.persistence.catalog

import io.github.carlomicieli.roundhouse.catalog.scales.Gauge
import io.github.carlomicieli.roundhouse.catalog.scales.Ratio
import io.github.carlomicieli.roundhouse.catalog.scales.ScaleId
import io.github.carlomicieli.roundhouse.catalog.scales.ScaleView
import io.github.carlomicieli.roundhouse.catalog.scales.Standard
import io.github.carlomicieli.roundhouse.catalog.scales.TrackGauge
import io.github.carlomicieli.roundhouse.lengths.Length
import io.github.carlomicieli.roundhouse.metadata.MetadataInfo
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

fun ScaleRow.toView(): ScaleView =
    ScaleView(
        id = this.scaleId,
        name = this.name,
        description = this.description,
        gauge =
            Gauge(
                millimetres = Length.ofMillimeters(this.gaugeMillimeters),
                inches = Length.ofInches(this.gaugeInches),
                trackGauge = trackGauge
            ),
        ratio = Ratio.of(this.ratio.toDouble()),
        standards = this.standards?.splitToSequence(',')?.map { fromString(it) }?.toSet() ?: setOf(),
        metadata =
            MetadataInfo(
                version = this.version,
                createdAt = this.created,
                lastModified = this.lastModified
            )
    )

// TODO: fix me
fun fromString(s: String): Standard {
    return if (s.isNotBlank()) {
        Standard.valueOf(s)
    } else {
        Standard.NEM
    }
}
