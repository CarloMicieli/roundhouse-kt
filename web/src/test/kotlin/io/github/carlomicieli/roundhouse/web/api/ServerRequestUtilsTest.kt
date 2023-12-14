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
package io.github.carlomicieli.roundhouse.web.api

import io.github.carlomicieli.roundhouse.queries.pagination.Page
import io.github.carlomicieli.roundhouse.queries.sorting.Direction
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.mock.web.reactive.function.server.MockServerRequest

@DisplayName("ServerRequest")
class ServerRequestUtilsTest {
    @Nested
    @DisplayName("pagination")
    inner class Pagination {
        @Test
        fun `fallback to the default pagination if no parameter is in the request`() {
            val request = MockServerRequest.builder().build()
            val page = request.page()

            page.limit shouldBe Page.DEFAULT_PAGE.limit
            page.start shouldBe Page.DEFAULT_PAGE.start
        }

        @Test
        fun `fallback to the default pagination limit if the parameter in the request is not a number`() {
            val request =
                MockServerRequest.builder()
                    .queryParam("limit", "NaN").build()
            val page = request.page()

            page.limit shouldBe Page.DEFAULT_PAGE.limit
        }

        @Test
        fun `fallback to the default pagination offset if the parameter in the request is not a number`() {
            val request =
                MockServerRequest.builder()
                    .queryParam("offset", "NaN").build()
            val page = request.page()

            page.start shouldBe Page.DEFAULT_PAGE.start
        }

        @Test
        fun `should use the page limit in the request`() {
            val request =
                MockServerRequest.builder()
                    .queryParam("limit", "5")
                    .build()
            val page = request.page()

            page.limit shouldBe 5
            page.start shouldBe Page.DEFAULT_PAGE.start
        }

        @Test
        fun `should use the page offset in the request`() {
            val request =
                MockServerRequest.builder()
                    .queryParam("offset", "5")
                    .build()
            val page = request.page()

            page.limit shouldBe Page.DEFAULT_PAGE.limit
            page.start shouldBe 5
        }
    }

    @Nested
    @DisplayName("sorting")
    inner class Sorting {
        @Test
        fun `fallback to the default sorting if no sorting parameter is in the request`() {
            val request = MockServerRequest.builder().build()
            val sorting = request.sorting()

            sorting.size shouldBe 0
        }

        @Test
        fun `should use the parameter in the request to build a single criteria sorting`() {
            val request =
                MockServerRequest.builder()
                    .queryParam("sort_by", "name.desc")
                    .build()
            val sorting = request.sorting()

            sorting.size shouldBe 1
            sorting["name"] shouldNotBe null
            sorting["name"]?.propertyName shouldBe "name"
            sorting["name"]?.direction shouldBe Direction.DESC
        }

        @Test
        fun `should default to ascending direction when not provided in the request`() {
            val request =
                MockServerRequest.builder()
                    .queryParam("sort_by", "name")
                    .build()
            val sorting = request.sorting()

            sorting.size shouldBe 1
            sorting["name"] shouldNotBe null
            sorting["name"]?.propertyName shouldBe "name"
            sorting["name"]?.direction shouldBe Direction.ASC
        }

        @Test
        fun `should use the parameters in the request to build a multiple criteria sorting`() {
            val request =
                MockServerRequest.builder()
                    .queryParam("sort_by", "name.desc,age.asc")
                    .build()
            val sorting = request.sorting()

            sorting.size shouldBe 2
            sorting["name"] shouldNotBe null
            sorting["name"]?.propertyName shouldBe "name"
            sorting["name"]?.direction shouldBe Direction.DESC

            sorting["age"] shouldNotBe null
            sorting["age"]?.propertyName shouldBe "age"
            sorting["age"]?.direction shouldBe Direction.ASC
        }
    }
}
