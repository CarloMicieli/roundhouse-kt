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
package com.trenako.web.api.queries

import com.trenako.queries.SingleResultQuery
import com.trenako.queries.criteria.Criteria
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

/**
 * A handler for query which returns zero or one result.
 */
@Transactional
interface SingleResultQueryHandler<C : Criteria, T> {
    val query: SingleResultQuery<C, T>
    val presenter: QueryResultPresenter<T>

    /**
     * Extract the query {@code Criteria} from the server request.
     * If it is not possible to extract the method will return a {@code null}
     *
     * @param serverRequest the server request
     * @return the query {@code Criteria} if the extraction from the request was successful
     */
    suspend fun extractCriteria(serverRequest: ServerRequest): C?

    suspend fun handle(serverRequest: ServerRequest): ServerResponse {
        val criteria = extractCriteria(serverRequest) ?: return ServerResponse.badRequest().buildAndAwait()
        val result = query.execute(criteria)
        return presenter.toServerResponse(result)
    }
}
