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
package io.github.carlomicieli.roundhouse.queries.result

import io.github.carlomicieli.roundhouse.queries.errors.QueryError
import io.github.carlomicieli.roundhouse.queries.pagination.Page

/**
 * A paginated result for a {@code Query}, it could be either a result set or an error
 *
 * @param <T> the view model data type
 */
sealed interface PaginatedResultSet<T> {
    data class Results<T>(val currentPage: Page, val items: List<T>) : PaginatedResultSet<T> {
        val previous: Boolean = currentPage.start > 0
        val next: Boolean = items.size >= currentPage.limit

        /**
         * Return the next page if available
         */
        fun nextPage(): Page? {
            return if (next) currentPage.next() else null
        }

        /**
         * Return the previous page if available
         */
        fun previousPage(): Page? {
            return if (previous) currentPage.previous() else null
        }
    }

    data class Error<T>(val queryError: QueryError) : PaginatedResultSet<T>
}
