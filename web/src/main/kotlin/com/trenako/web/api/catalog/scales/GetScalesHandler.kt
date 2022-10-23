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

import com.trenako.catalog.scales.getscales.GetScalesQuery
import com.trenako.queries.sorting.Direction
import com.trenako.queries.sorting.Sorting
import com.trenako.web.api.page
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

class GetScalesHandler(private val getScalesQuery: GetScalesQuery, private val presenter: GetScalesPresenter) {
    suspend fun handle(serverRequest: ServerRequest): ServerResponse {
        val page = serverRequest.page()
        val sorting = Sorting.by("scale_id", Direction.ASC).build()

        val result = getScalesQuery.execute(page, sorting)
        return presenter.toServerResponse(result)
    }
}
