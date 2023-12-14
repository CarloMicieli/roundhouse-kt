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

import io.github.carlomicieli.roundhouse.catalog.brands.createbrands.CreateBrand
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.mockito.kotlin.any
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@DisplayName("Brand routes")
@TestInstance(Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class BrandRoutingTest {
    private lateinit var createBrandHandler: CreateBrandHandler
    private lateinit var getBrandByIdHandler: GetBrandByIdHandler
    private lateinit var getBrandsHandler: GetBrandsHandler
    private lateinit var webclient: WebTestClient

    @BeforeAll
    fun setup() {
        createBrandHandler = mock()
        getBrandByIdHandler = mock()
        getBrandsHandler = mock()
        webclient =
            WebTestClient.bindToRouterFunction(
                Brands.routes(createBrandHandler, getBrandByIdHandler, getBrandsHandler)
            ).build()
    }

    @Test
    @DisplayName("POST /api/brands is mapped correctly")
    fun postNewBrandTest() =
        runTest {
            whenever(createBrandHandler.handle(any())).doSuspendableAnswer {
                ServerResponse.ok().bodyValueAndAwait("works")
            }

            val bodyValue = CreateBrand()

            webclient.post()
                .uri("/api/brands")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bodyValue)
                .exchange()
                .expectStatus().isOk
        }

    @Test
    @DisplayName("GET /api/brands/{brand} is mapped correctly")
    fun getBrandByIdTest() =
        runTest {
            whenever(getBrandByIdHandler.handle(any())).doSuspendableAnswer {
                ServerResponse.ok().bodyValueAndAwait("works")
            }

            webclient.get()
                .uri("/api/brands/{brand}", "acme")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
        }

    @Test
    @DisplayName("GET /api/brands is mapped correctly")
    fun getBrandsTest() =
        runTest {
            whenever(getBrandsHandler.handle(any())).doSuspendableAnswer {
                ServerResponse.ok().bodyValueAndAwait("works")
            }

            webclient.get()
                .uri("/api/brands")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
        }
}
