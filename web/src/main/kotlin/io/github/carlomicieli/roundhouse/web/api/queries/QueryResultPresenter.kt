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
import io.github.carlomicieli.roundhouse.queries.result.SingleResult
import io.github.carlomicieli.roundhouse.web.api.toServerResponse
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

interface QueryResultPresenter<T> {
    val problemDetailsGenerator: ProblemDetailsGenerator

    suspend fun result(value: T): ServerResponse

    suspend fun toServerResponse(result: SingleResult<T?>): ServerResponse =
        when (result) {
            is SingleResult.Result -> {
                val resultValue = result.value
                if (resultValue == null) {
                    ServerResponse.notFound().buildAndAwait()
                } else {
                    result(resultValue)
                }
            }

            is SingleResult.Error ->
                problemDetailsGenerator.error(result.queryError.reason)
                    .toServerResponse()
        }
}
