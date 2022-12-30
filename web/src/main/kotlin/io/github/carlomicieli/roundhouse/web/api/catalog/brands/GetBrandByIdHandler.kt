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
package io.github.carlomicieli.roundhouse.web.api.catalog.brands

import io.github.carlomicieli.roundhouse.catalog.brands.BrandId
import io.github.carlomicieli.roundhouse.catalog.brands.BrandView
import io.github.carlomicieli.roundhouse.catalog.brands.getbrandbyid.ByBrandId
import io.github.carlomicieli.roundhouse.catalog.brands.getbrandbyid.GetBrandByIdQuery
import io.github.carlomicieli.roundhouse.web.api.queries.QueryResultPresenter
import io.github.carlomicieli.roundhouse.web.api.queries.SingleResultQueryHandler
import org.springframework.web.reactive.function.server.ServerRequest

class GetBrandByIdHandler(
    override val query: GetBrandByIdQuery,
    override val presenter: QueryResultPresenter<BrandView>
) : SingleResultQueryHandler<ByBrandId, BrandView> {
    override suspend fun extractCriteria(serverRequest: ServerRequest): ByBrandId? {
        val pathVariable = serverRequest.pathVariable("brand")
        return if (pathVariable.isBlank()) {
            null
        } else {
            ByBrandId(io.github.carlomicieli.roundhouse.catalog.brands.BrandId.of(pathVariable))
        }
    }
}
