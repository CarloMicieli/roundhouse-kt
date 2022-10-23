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
package com.trenako.catalog.scales.getscales

import com.trenako.catalog.scales.ScaleView
import com.trenako.queries.PaginatedQuery
import com.trenako.queries.errors.QueryError
import com.trenako.queries.pagination.Page
import com.trenako.queries.result.PaginatedResultSet
import com.trenako.queries.sorting.Sorting
import kotlinx.coroutines.flow.toList

class GetScalesQuery(private val getScalesRepository: GetScalesRepository) : PaginatedQuery<ScaleView> {
    override suspend fun execute(currentPage: Page, orderBy: Sorting): PaginatedResultSet<ScaleView> = try {
        val results: List<ScaleView> = getScalesRepository.findAll(currentPage, orderBy).toList()
        PaginatedResultSet.Results(currentPage, results)
    } catch (ex: Exception) {
        PaginatedResultSet.Error(QueryError.DatabaseError(ex))
    }
}
