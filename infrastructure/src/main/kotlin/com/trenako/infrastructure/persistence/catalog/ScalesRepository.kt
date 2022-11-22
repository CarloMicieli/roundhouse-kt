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

import com.trenako.catalog.scales.createscales.CreateScaleRepository
import com.trenako.catalog.scales.createscales.NewScale
import com.trenako.infrastructure.persistence.queries.selectOne
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import java.time.Clock

class ScalesRepository(private val r2dbcEntityTemplate: R2dbcEntityTemplate, private val clock: Clock) :
    CreateScaleRepository {

    override suspend fun exists(name: String): Boolean {
        val query = selectOne {
            Criteria.where("name").`is`(name)
        }
        return r2dbcEntityTemplate
            .exists(query, ENTITY)
            .awaitSingle()
    }

    override suspend fun insert(newScale: NewScale) {
        val newRow = ScaleRow(
            scaleId = newScale.id,
            name = newScale.name,
            description = newScale.description,
            ratio = newScale.ratio.value,
            gaugeMillimeters = newScale.gauge.millimetres.value,
            gaugeInches = newScale.gauge.inches.value,
            trackGauge = newScale.gauge.trackGauge,
            standards = newScale.standards.joinToString(),
            version = 0,
            created = clock.instant(),
            lastModified = null
        )
        r2dbcEntityTemplate.insert(newRow).awaitSingle()
    }

    companion object {
        private val ENTITY: Class<ScaleRow> = ScaleRow::class.java
    }
}
