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

import io.github.carlomicieli.roundhouse.catalog.scales.createscales.CreateScale
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.kotlin.any
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@DisplayName("Scale routes")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class ScaleRoutingTest {

    private lateinit var createScaleHandler: CreateScaleHandler
    private lateinit var getScaleByIdHandler: GetScaleByIdHandler
    private lateinit var getScalesHandler: GetScalesHandler
    private lateinit var webclient: WebTestClient

    @BeforeAll
    fun setup() {
        createScaleHandler = mock()
        getScaleByIdHandler = mock()
        getScalesHandler = mock()
        webclient = WebTestClient.bindToRouterFunction(
            Scales.routes(createScaleHandler, getScaleByIdHandler, getScalesHandler)
        ).build()
    }

    @Test
    @DisplayName("POST /api/scales is mapped correctly")
    fun postNewScaleTest() = runTest {
        whenever(createScaleHandler.handle(any())).doSuspendableAnswer {
            ServerResponse.ok().bodyValueAndAwait("works")
        }

        val bodyValue = CreateScale()

        webclient.post()
            .uri("/api/scales")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(bodyValue)
            .exchange()
            .expectStatus().isOk
    }

    @Test
    @DisplayName("GET /api/scales/{scale} is mapped correctly")
    fun getScaleByIdTest() = runTest {
        whenever(getScaleByIdHandler.handle(any())).doSuspendableAnswer {
            ServerResponse.ok().bodyValueAndAwait("works")
        }

        webclient.get()
            .uri("/api/scales/{scale}", "acme")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
    }

    @Test
    @DisplayName("GET /api/scales is mapped correctly")
    fun getScalesTest() = runTest {
        whenever(getScalesHandler.handle(any())).doSuspendableAnswer { ServerResponse.ok().bodyValueAndAwait("works") }

        webclient.get()
            .uri("/api/scales")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
    }
}
