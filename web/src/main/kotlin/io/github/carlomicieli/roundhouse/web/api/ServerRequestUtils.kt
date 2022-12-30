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
import io.github.carlomicieli.roundhouse.queries.sorting.Sorting
import org.springframework.web.reactive.function.server.ServerRequest
import java.util.Optional

fun ServerRequest.page(): Page {
    val limit = this.queryParam("limit").asInt().orElseGet { Page.DEFAULT_PAGE.limit }
    val start = this.queryParam("offset").asInt().orElseGet { Page.DEFAULT_PAGE.start }
    return Page(start, limit)
}

private fun Optional<String>.asInt(): Optional<Int> = this.map { it.toIntOrNull() }

fun ServerRequest.sorting(): Sorting = this.queryParam("sort_by").map { extractSorting(it) }.orElseGet { Sorting.DEFAULT_SORT }

private fun extractSorting(input: String): Sorting {
    val tokens = input.split(',')

    val first = tokens.first()
    val (propName, dir) = extractCriteria(first)

    val sortingBuilder = Sorting.by(propName, dir)

    tokens.drop(1)
        .map { extractCriteria(it) }
        .forEach {
            sortingBuilder.andThenBy(it.first, it.second)
        }

    return sortingBuilder.build()
}

private fun extractCriteria(s: String): Pair<String, Direction> {
    val tokens = s.split('.')
    val propertyName = tokens[0].trim()
    val dir = if (tokens.size == 2) { tokens[1].trim() } else { "asc" }
    return Pair(propertyName, direction(dir))
}

private fun direction(s: String): Direction =
    if (s.equals(Direction.DESC.name, true)) {
        Direction.DESC
    } else {
        Direction.ASC
    }
