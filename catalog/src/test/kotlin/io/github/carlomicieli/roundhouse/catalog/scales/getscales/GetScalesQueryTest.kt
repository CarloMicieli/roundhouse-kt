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
package io.github.carlomicieli.roundhouse.catalog.scales.getscales

import io.github.carlomicieli.roundhouse.catalog.scales.Gauge
import io.github.carlomicieli.roundhouse.catalog.scales.Ratio
import io.github.carlomicieli.roundhouse.catalog.scales.ScaleId
import io.github.carlomicieli.roundhouse.catalog.scales.ScaleView
import io.github.carlomicieli.roundhouse.catalog.scales.Standard
import io.github.carlomicieli.roundhouse.catalog.scales.TrackGauge
import io.github.carlomicieli.roundhouse.lengths.Length
import io.github.carlomicieli.roundhouse.lengths.MeasureUnit
import io.github.carlomicieli.roundhouse.metadata.MetadataInfo
import io.github.carlomicieli.roundhouse.queries.pagination.Page
import io.github.carlomicieli.roundhouse.queries.result.PaginatedResultSet
import io.github.carlomicieli.roundhouse.queries.sorting.Sorting
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.Instant

@DisplayName("GetScalesQuery")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class GetScalesQueryTest {
    private lateinit var query: GetScalesQuery
    private lateinit var getScalesRepository: GetScalesRepository

    @BeforeEach
    fun setup() {
        getScalesRepository = mock()
        query = GetScalesQuery(getScalesRepository)
    }

    @Test
    fun `should return a result when the scales are found`() = runTest {
        val currentPage = Page.DEFAULT_PAGE
        val sorting = Sorting.DEFAULT_SORT

        whenever(getScalesRepository.findAll(currentPage, sorting)).doAnswer { scalesFlow() }

        val result = query.execute(currentPage, sorting)

        result shouldBe PaginatedResultSet.Results(currentPage, scalesList())
    }

    @Test
    fun `should handle exception executing the query`() = runTest {
        val currentPage = Page.DEFAULT_PAGE
        val sorting = Sorting.DEFAULT_SORT

        whenever(getScalesRepository.findAll(currentPage, sorting)).thenThrow(RuntimeException("Ops, something went wrong"))

        val result = query.execute(currentPage, sorting)

        val errorResult = result as? PaginatedResultSet.Error
        errorResult shouldNotBe null
        errorResult?.queryError?.reason shouldBe "An error has occurred"
    }

    @Test
    fun `should return a result when no scales are found`() = runTest {
        val currentPage = Page.DEFAULT_PAGE
        val sorting = Sorting.DEFAULT_SORT

        whenever(getScalesRepository.findAll(currentPage, sorting)).doAnswer { emptyFlow() }

        val result = query.execute(currentPage, sorting)

        result shouldBe PaginatedResultSet.Results(currentPage, listOf())
    }

    private fun scalesList(): List<ScaleView> = (1 until 10)
        .map { "Scale$it" }
        .map { scaleView(ScaleId.of(it)) }

    private fun scalesFlow(): Flow<ScaleView> = flow {
        scalesList()
            .forEach { emit(it) }
    }

    private fun scaleView(id: ScaleId) = ScaleView(
        id = id,
        name = id.toString(),
        ratio = Ratio.of(87.0f),
        gauge = Gauge(Length.valueOf(16.5, MeasureUnit.MILLIMETERS), Length.valueOf(0.65, MeasureUnit.INCHES), TrackGauge.STANDARD),
        description = "My test Scale",
        standards = setOf(Standard.NEM),
        metadata = MetadataInfo(1, createdAt = Instant.ofEpochMilli(1661021655290L))
    )
}
