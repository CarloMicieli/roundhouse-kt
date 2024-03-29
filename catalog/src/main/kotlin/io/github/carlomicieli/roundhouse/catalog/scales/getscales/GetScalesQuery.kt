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

import io.github.carlomicieli.roundhouse.catalog.scales.ScaleView
import io.github.carlomicieli.roundhouse.queries.PaginatedQuery
import io.github.carlomicieli.roundhouse.queries.errors.QueryError
import io.github.carlomicieli.roundhouse.queries.pagination.Page
import io.github.carlomicieli.roundhouse.queries.result.PaginatedResultSet
import io.github.carlomicieli.roundhouse.queries.sorting.Sorting
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory

class GetScalesQuery(private val getScalesRepository: GetScalesRepository) : PaginatedQuery<ScaleView> {
    companion object {
        val log = LoggerFactory.getLogger(GetScalesQuery::class.java)
    }

    override suspend fun execute(
        currentPage: Page,
        orderBy: Sorting
    ): PaginatedResultSet<ScaleView> =
        try {
            val results: List<ScaleView> = getScalesRepository.findAll(currentPage, orderBy).toList()
            PaginatedResultSet.Results(currentPage, results)
        } catch (ex: Exception) {
            log.error("GetScalesQuery", ex)
            PaginatedResultSet.Error(QueryError.DatabaseError(ex))
        }
}
