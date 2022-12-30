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
package io.github.carlomicieli.roundhouse.queries.pagination

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Page")
class PageTest {
    @Test
    fun `must have a non negative starting index`() {
        val ex = shouldThrowExactly<IllegalArgumentException> {
            Page(-1, 25)
        }
        ex.message shouldBe "Page starting index cannot be negative"
    }

    @Test
    fun `must have a non negative page size`() {
        val ex = shouldThrowExactly<IllegalArgumentException> {
            Page(0, -1)
        }
        ex.message shouldBe "Page limit cannot be negative"
    }

    @Test
    fun `should create a default Page`() {
        val page = Page.DEFAULT_PAGE
        page.start shouldBe 0
        page.limit shouldBe 25
    }

    @Test
    fun `should return the next page`() {
        val page = Page(20, 10)
        val nextPage = page.next()

        nextPage.start shouldBe 30
        nextPage.limit shouldBe 10
    }

    @Test
    fun `should return the previous page`() {
        val page = Page(20, 10)
        val previousPage = page.previous()

        previousPage.start shouldBe 10
        previousPage.limit shouldBe 10
    }

    @Test
    fun `should return the first page asking for the previous page when the current one is near the start`() {
        val page = Page(5, 10)
        val previousPage = page.previous()

        previousPage.start shouldBe 0
        previousPage.limit shouldBe 10
    }
}
