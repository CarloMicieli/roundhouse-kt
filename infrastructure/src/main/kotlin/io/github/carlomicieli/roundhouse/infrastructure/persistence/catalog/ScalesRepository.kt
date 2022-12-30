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

import io.github.carlomicieli.roundhouse.catalog.scales.ScaleId
import io.github.carlomicieli.roundhouse.catalog.scales.ScaleView
import io.github.carlomicieli.roundhouse.catalog.scales.createscales.CreateScaleRepository
import io.github.carlomicieli.roundhouse.catalog.scales.createscales.NewScale
import io.github.carlomicieli.roundhouse.catalog.scales.getscalebyid.GetScaleByIdRepository
import io.github.carlomicieli.roundhouse.catalog.scales.getscales.GetScalesRepository
import io.github.carlomicieli.roundhouse.infrastructure.persistence.queries.select
import io.github.carlomicieli.roundhouse.infrastructure.persistence.queries.selectOne
import io.github.carlomicieli.roundhouse.queries.pagination.Page
import io.github.carlomicieli.roundhouse.queries.sorting.Sorting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import java.time.Clock

class ScalesRepository(private val r2dbcEntityTemplate: R2dbcEntityTemplate, private val clock: Clock) :
    CreateScaleRepository,
    GetScaleByIdRepository,
    GetScalesRepository {

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

    override suspend fun findById(scaleId: ScaleId): ScaleView? {
        val query = selectOne {
            Criteria.where("scale_id").`is`(scaleId.toString())
        }
        return r2dbcEntityTemplate
            .selectOne(query, ENTITY)
            .map { it.toView() }
            .awaitSingleOrNull()
    }

    override fun findAll(currentPage: Page, orderBy: Sorting): Flow<ScaleView> {
        val query = select(currentPage, orderBy)
        return r2dbcEntityTemplate
            .select(query, ENTITY)
            .map { it.toView() }
            .asFlow()
    }

    companion object {
        private val ENTITY: Class<ScaleRow> = ScaleRow::class.java
    }
}
