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
package io.github.carlomicieli.roundhouse.web.api.catalog.scales

import io.github.carlomicieli.roundhouse.catalog.scales.ScaleView
import io.github.carlomicieli.roundhouse.problems.ProblemDetailsGenerator
import io.github.carlomicieli.roundhouse.web.api.queries.QueryResultPresenter
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.Link
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

class GetScaleByIdPresenter(override val problemDetailsGenerator: ProblemDetailsGenerator) :
    QueryResultPresenter<ScaleView> {
    override suspend fun result(value: ScaleView): ServerResponse {
        val self = Link.of("/api/scales/${value.id}", IanaLinkRelations.SELF)
        val body = EntityModel.of(value, self)

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(body)
    }
}
