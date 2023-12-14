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
package io.github.carlomicieli.roundhouse.queries.sorting

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Sorting")
class SortingTest {
    @Test
    fun `should default to no sorting criteria`() {
        val defaultSorting = Sorting.DEFAULT_SORT
        defaultSorting.size shouldBe 0
        defaultSorting.criteriaList shouldHaveSize 0
    }

    @Test
    fun `is built adding sorting criteria`() {
        val sorting =
            Sorting.by("name", Direction.ASC)
                .andThenBy("age", Direction.DESC)
                .build()

        sorting.size shouldBe 2
        sorting.criteriaList shouldHaveSize 2
        sorting.criteriaList shouldContain SortCriteria("name", Direction.ASC)
        sorting.criteriaList shouldContain SortCriteria("age", Direction.DESC)
    }

    @Test
    fun `should return the sorting criteria by property name`() {
        val sorting =
            Sorting.by("name", Direction.ASC)
                .andThenBy("age", Direction.DESC)
                .build()

        val criteria = sorting["name"]
        criteria shouldNotBe null
        criteria?.propertyName shouldBe "name"
        criteria?.direction shouldBe Direction.ASC
    }

    @Test
    fun `should return null when the property name is not included in the sorting criteria`() {
        val sorting =
            Sorting.by("name", Direction.ASC)
                .andThenBy("age", Direction.DESC)
                .build()

        val criteria = sorting["notFound"]
        criteria shouldBe null
    }
}
