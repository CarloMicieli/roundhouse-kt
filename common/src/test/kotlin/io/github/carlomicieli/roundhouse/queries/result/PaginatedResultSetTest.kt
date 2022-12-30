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
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.lang.Exception

@DisplayName("PaginatedResultSet")
class PaginatedResultSetTest {

    @Nested
    @DisplayName("Results")
    inner class ResultsTest {
        @Test
        fun `should create a paginated result set for the first page`() {
            val currentPage = Page(0, 2)
            val result = PaginatedResultSet.Results(currentPage, listOf("one", "two"))
            result.currentPage shouldBe currentPage
            result.next shouldBe true
            result.previous shouldBe false
            result.items shouldHaveSize 2
        }

        @Test
        fun `should create a paginated result set for the second page`() {
            val currentPage = Page(2, 2)
            val result = PaginatedResultSet.Results(currentPage, listOf("one", "two"))
            result.currentPage shouldBe currentPage
            result.next shouldBe true
            result.previous shouldBe true
            result.items shouldHaveSize 2
        }

        @Test
        fun `should have no next page when the items are less than the page size`() {
            val currentPage = Page(0, 10)
            val result = PaginatedResultSet.Results(currentPage, listOf("one", "two"))
            result.currentPage shouldBe currentPage
            result.next shouldBe false
            result.previous shouldBe false
            result.items shouldHaveSize 2
        }

        @Test
        fun `should return the next page`() {
            val currentPage = Page(0, 2)
            val result = PaginatedResultSet.Results(currentPage, listOf("one", "two"))
            result.nextPage() shouldBe Page(2, 2)
        }

        @Test
        fun `should return the previous page`() {
            val currentPage = Page(2, 2)
            val result = PaginatedResultSet.Results(currentPage, listOf("one", "two"))
            result.previousPage() shouldBe Page(0, 2)
        }

        @Test
        fun `should return null when there is no next page`() {
            val currentPage = Page(0, 10)
            val result = PaginatedResultSet.Results(currentPage, listOf("one", "two"))
            result.nextPage() shouldBe null
        }

        @Test
        fun `should return null when there is no previous page`() {
            val currentPage = Page(0, 2)
            val result = PaginatedResultSet.Results(currentPage, listOf("one", "two"))
            result.previousPage() shouldBe null
        }
    }

    @Nested
    @DisplayName("Results")
    inner class ErrorTest {
        @Test
        fun `should create an error from a database error, without sharing the exception message`() {
            val error = PaginatedResultSet.Error<String>(QueryError.DatabaseError(Exception("don't share this")))
            error.queryError.reason shouldBe "An error has occurred"
        }
    }
}
