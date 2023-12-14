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
import io.github.carlomicieli.roundhouse.queries.pagination.Page
import io.github.carlomicieli.roundhouse.queries.result.PaginatedResultSet
import io.github.carlomicieli.roundhouse.web.api.toServerResponse
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.Link
import org.springframework.hateoas.LinkRelation
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

interface QueryResultSetPresenter<T> {
    val problemDetailsGenerator: ProblemDetailsGenerator

    suspend fun results(results: PaginatedResultSet.Results<T>): ServerResponse

    suspend fun toServerResponse(results: PaginatedResultSet<T>): ServerResponse =
        when (results) {
            is PaginatedResultSet.Results -> {
                val resultValue = results.items.size
                if (resultValue == 0) {
                    ServerResponse.notFound().buildAndAwait()
                } else {
                    results(results)
                }
            }

            is PaginatedResultSet.Error ->
                problemDetailsGenerator.error(results.queryError.reason)
                    .toServerResponse()
        }
}

fun <T> PaginatedResultSet.Results<T>.paginationLinks(baseUrl: String): List<Link> {
    val selfLink = this.currentPage.toLink(baseUrl, IanaLinkRelations.SELF)
    val prevPageLink = if (this.previous) this.currentPage.previous().toLink(baseUrl, IanaLinkRelations.PREV) else null
    val nextPageLink = if (this.next) this.currentPage.next().toLink(baseUrl, IanaLinkRelations.NEXT) else null

    return if (prevPageLink != null && nextPageLink != null) {
        listOf(selfLink, prevPageLink, nextPageLink)
    } else if (prevPageLink != null) {
        listOf(selfLink, prevPageLink)
    } else if (nextPageLink != null) {
        listOf(selfLink, nextPageLink)
    } else {
        listOf(selfLink)
    }
}

fun Page.toLink(
    baseUrl: String,
    relation: LinkRelation
): Link {
    return Link.of("$baseUrl?offset=${this.start}&limit=${this.limit}", relation)
}
