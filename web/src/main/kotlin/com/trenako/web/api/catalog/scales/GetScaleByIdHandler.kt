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
package com.trenako.web.api.catalog.scales

import com.trenako.catalog.scales.ScaleId
import com.trenako.catalog.scales.ScaleView
import com.trenako.catalog.scales.getscalebyid.ByScaleId
import com.trenako.catalog.scales.getscalebyid.GetScaleByIdQuery
import com.trenako.web.api.queries.QueryResultPresenter
import com.trenako.web.api.queries.SingleResultQueryHandler
import org.springframework.web.reactive.function.server.ServerRequest

class GetScaleByIdHandler(
    override val query: GetScaleByIdQuery,
    override val presenter: QueryResultPresenter<ScaleView>
) : SingleResultQueryHandler<ByScaleId, ScaleView> {
    override suspend fun extractCriteria(serverRequest: ServerRequest): ByScaleId? {
        val pathVariable = serverRequest.pathVariable("scale")
        return if (pathVariable.isBlank()) {
            null
        } else {
            ByScaleId(ScaleId.of(pathVariable))
        }
    }
}
