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
package com.trenako.catalog.brands.getbrandbyid

import com.trenako.catalog.brands.BrandId
import com.trenako.catalog.brands.BrandKind
import com.trenako.catalog.brands.BrandStatus
import com.trenako.catalog.brands.BrandView
import com.trenako.metadata.MetadataInfo
import com.trenako.queries.result.SingleResult
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

@DisplayName("GetBrandByIdQuery")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class GetBrandByIdQueryTest {
    private lateinit var query: GetBrandByIdQuery
    private lateinit var getBrandByIdRepository: GetBrandByIdRepository

    @BeforeAll
    fun setup() {
        getBrandByIdRepository = mock()
        query = GetBrandByIdQuery(getBrandByIdRepository)
    }

    @Test
    fun `should return a result when the brand is found`() = runTest {
        val brandId = BrandId.of("acme")
        whenever(getBrandByIdRepository.findById(brandId)).doSuspendableAnswer { brandView(brandId) }

        val criteria = ByBrandId(brandId)
        val result = query.execute(criteria)

        result shouldBe SingleResult.Result(brandView(brandId))
    }

    @Test
    fun `should return a result when the brand is not found`() = runTest {
        val brandId = BrandId.of("acme")
        whenever(getBrandByIdRepository.findById(brandId)).doSuspendableAnswer { null }

        val criteria = ByBrandId(brandId)
        val result = query.execute(criteria)

        result shouldBe SingleResult.Result(null)
    }

    @Test
    fun `should handle exception executing the query`() = runTest {
        val brandId = BrandId.of("bad")
        whenever(getBrandByIdRepository.findById(brandId)).thenThrow(RuntimeException("Ops, something went wrong"))

        val criteria = ByBrandId(brandId)
        val result = query.execute(criteria)

        val errorResult = result as? SingleResult.Error
        errorResult shouldNotBe null
        errorResult?.queryError?.reason shouldBe "Ops, something went wrong"
    }

    private fun brandView(id: BrandId) = BrandView(
        id = id,
        name = id.toString(),
        registeredCompanyName = null,
        groupName = null,
        description = "My test brand",
        address = null,
        contactInfo = null,
        kind = BrandKind.INDUSTRIAL,
        status = BrandStatus.ACTIVE,
        metadata = MetadataInfo(1, createdAt = Instant.ofEpochMilli(1661021655290L))
    )
}
