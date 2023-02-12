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
package io.github.carlomicieli.roundhouse.catalog.scales.getscalebyid

import io.github.carlomicieli.roundhouse.catalog.scales.Gauge
import io.github.carlomicieli.roundhouse.catalog.scales.Ratio
import io.github.carlomicieli.roundhouse.catalog.scales.ScaleId
import io.github.carlomicieli.roundhouse.catalog.scales.ScaleView
import io.github.carlomicieli.roundhouse.catalog.scales.Standard
import io.github.carlomicieli.roundhouse.catalog.scales.TrackGauge
import io.github.carlomicieli.roundhouse.lengths.Length
import io.github.carlomicieli.roundhouse.lengths.MeasureUnit
import io.github.carlomicieli.roundhouse.metadata.MetadataInfo
import io.github.carlomicieli.roundhouse.queries.result.SingleResult
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.Instant

@DisplayName("GetScaleByIdQuery")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class GetScaleByIdQueryTest {
    private lateinit var query: GetScaleByIdQuery
    private lateinit var getScaleByIdRepository: GetScaleByIdRepository

    @BeforeAll
    fun setup() {
        getScaleByIdRepository = mock()
        query = GetScaleByIdQuery(getScaleByIdRepository)
    }

    @Test
    fun `should return a result when the Scale is found`() = runTest {
        val scaleId = ScaleId.of("acme")
        whenever(getScaleByIdRepository.findById(scaleId)).doSuspendableAnswer { scaleView(scaleId) }

        val criteria = ByScaleId(scaleId)
        val result = query.execute(criteria)

        result shouldBe SingleResult.Result(scaleView(scaleId))
    }

    @Test
    fun `should return a result when the Scale is not found`() = runTest {
        val scaleId = ScaleId.of("acme")
        whenever(getScaleByIdRepository.findById(scaleId)).doSuspendableAnswer { null }

        val criteria = ByScaleId(scaleId)
        val result = query.execute(criteria)

        result shouldBe SingleResult.Result(null)
    }

    @Test
    fun `should handle exception executing the query`() = runTest {
        val scaleId = ScaleId.of("bad")
        whenever(getScaleByIdRepository.findById(scaleId)).thenThrow(RuntimeException("Ops, something went wrong"))

        val criteria = ByScaleId(scaleId)
        val result = query.execute(criteria)

        val errorResult = result as? SingleResult.Error
        errorResult shouldNotBe null
        errorResult?.queryError?.reason shouldBe "An error has occurred"
    }

    private fun scaleView(id: ScaleId) = ScaleView(
        id = id,
        name = id.toString(),
        ratio = Ratio.of(87.0f),
        gauge = Gauge(
            Length.valueOf(16.5, MeasureUnit.MILLIMETERS),
            Length.valueOf(0.65, MeasureUnit.INCHES),
            TrackGauge.STANDARD
        ),
        description = "My test Scale",
        standards = setOf(Standard.NEM),
        metadata = MetadataInfo(1, createdAt = Instant.ofEpochMilli(1661021655290L))
    )
}
