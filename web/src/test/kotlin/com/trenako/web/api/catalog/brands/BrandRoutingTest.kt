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
package com.trenako.web.api.catalog.brands

import com.trenako.catalog.brands.createbrands.CreateBrand
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@DisplayName("Brand routes")
@TestInstance(Lifecycle.PER_CLASS)
@OptIn(ExperimentalCoroutinesApi::class)
class BrandRoutingTest {

    private lateinit var createBrandHandler: CreateBrandHandler
    private lateinit var webclient: WebTestClient

    @BeforeAll
    fun setup() {
        createBrandHandler = mockk()
        webclient = WebTestClient.bindToRouterFunction(Brands.routes(createBrandHandler)).build()
    }

    @Test
    @DisplayName("POST /api/brands is mapped correctly")
    fun postNewBrandTest() = runTest {
        coEvery { createBrandHandler.handle(any()) } returns ServerResponse.ok().bodyValueAndAwait("works")

        val bodyValue = CreateBrand()

        webclient.post()
            .uri("/api/brands")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(bodyValue)
            .exchange()
            .expectStatus().isOk
    }
}
