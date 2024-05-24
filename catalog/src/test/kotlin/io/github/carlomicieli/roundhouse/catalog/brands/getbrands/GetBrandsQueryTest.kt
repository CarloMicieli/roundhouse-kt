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
package io.github.carlomicieli.roundhouse.catalog.brands.getbrands

import io.github.carlomicieli.roundhouse.catalog.brands.BrandId
import io.github.carlomicieli.roundhouse.catalog.brands.BrandKind
import io.github.carlomicieli.roundhouse.catalog.brands.BrandStatus
import io.github.carlomicieli.roundhouse.catalog.brands.BrandView
import io.github.carlomicieli.roundhouse.metadata.MetadataInfo
import io.github.carlomicieli.roundhouse.queries.pagination.Page
import io.github.carlomicieli.roundhouse.queries.result.PaginatedResultSet
import io.github.carlomicieli.roundhouse.queries.sorting.Sorting
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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

@DisplayName("GetBrandsByQuery")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetBrandsQueryTest {
    private lateinit var query: GetBrandsQuery
    private lateinit var getBrandsRepository: GetBrandsRepository

    @BeforeEach
    fun setup() {
        getBrandsRepository = mock()
        query = GetBrandsQuery(getBrandsRepository)
    }

    @Test
    fun `should return a result when the brands are found`() =
        runTest {
            val currentPage = Page.DEFAULT_PAGE
            val sorting = Sorting.DEFAULT_SORT

            whenever(getBrandsRepository.findAll(currentPage, sorting)).doAnswer { brandsFlow() }

            val result = query.execute(currentPage, sorting)

            result shouldBe PaginatedResultSet.Results(currentPage, brandsList())
        }

    @Test
    fun `should handle exception executing the query`() =
        runTest {
            val currentPage = Page.DEFAULT_PAGE
            val sorting = Sorting.DEFAULT_SORT

            whenever(getBrandsRepository.findAll(currentPage, sorting)).thenThrow(
                RuntimeException("Ops, something went wrong")
            )

            val result = query.execute(currentPage, sorting)

            val errorResult = result as? PaginatedResultSet.Error
            errorResult shouldNotBe null
            errorResult?.queryError?.reason shouldBe "An error has occurred"
        }

    @Test
    fun `should return a result when no brands are found`() =
        runTest {
            val currentPage = Page.DEFAULT_PAGE
            val sorting = Sorting.DEFAULT_SORT

            whenever(getBrandsRepository.findAll(currentPage, sorting)).doAnswer { emptyFlow() }

            val result = query.execute(currentPage, sorting)

            result shouldBe PaginatedResultSet.Results(currentPage, listOf())
        }

    private fun brandsList(): List<BrandView> =
        (1 until 10)
            .map { "Brand$it" }
            .map { brandView(BrandId.of(it)) }

    private fun brandsFlow(): Flow<BrandView> =
        flow {
            brandsList()
                .forEach { emit(it) }
        }

    private fun brandView(id: BrandId) =
        BrandView(
            id = id,
            name = id.toString(),
            registeredCompanyName = null,
            organizationEntityType = null,
            groupName = null,
            description = "My test brand",
            address = null,
            contactInfo = null,
            socials = null,
            kind = BrandKind.INDUSTRIAL,
            status = BrandStatus.ACTIVE,
            metadata = MetadataInfo(1, createdAt = Instant.ofEpochMilli(1661021655290L))
        )
}
