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
package io.github.carlomicieli.roundhouse.web.api.queries

import io.github.carlomicieli.roundhouse.problems.ProblemDetailsGenerator
import io.github.carlomicieli.roundhouse.queries.errors.QueryError
import io.github.carlomicieli.roundhouse.queries.pagination.Page
import io.github.carlomicieli.roundhouse.queries.result.PaginatedResultSet
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.lang.Exception

@DisplayName("QueryResultSetPresenter")
class QueryResultSetPresenterTest {
    private val presenter = queryResultSetPresenter()

    @Test
    fun `should create a response with the result set`() =
        runTest {
            val result = PaginatedResultSet.Results(Page.DEFAULT_PAGE, listOf("hello world"))
            val response = presenter.toServerResponse(result)
            response.statusCode() shouldBe HttpStatus.OK
        }

    @Test
    fun `should create a NOT_FOUND response`() =
        runTest {
            val response = presenter.toServerResponse(PaginatedResultSet.Results(Page.DEFAULT_PAGE, listOf()))
            response.statusCode() shouldBe HttpStatus.NOT_FOUND
        }

    @Test
    fun `should create a INTERNAL_SERVER_ERROR response`() =
        runTest {
            val response =
                presenter.toServerResponse(
                    PaginatedResultSet.Error(QueryError.DatabaseError(Exception("ops")))
                )
            response.statusCode() shouldBe HttpStatus.INTERNAL_SERVER_ERROR
        }

    private fun queryResultSetPresenter(): QueryResultSetPresenter<String> =
        object : QueryResultSetPresenter<String> {
            override val problemDetailsGenerator: ProblemDetailsGenerator
                get() = ProblemDetailsGenerator.default()

            override suspend fun results(results: PaginatedResultSet.Results<String>): ServerResponse =
                ServerResponse.ok().bodyValueAndAwait(results)
        }
}
