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

import io.github.carlomicieli.roundhouse.AbstractWebApiTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType

@DisplayName("GET /api/brands/{brand}")
class GetBrandByIdIntegrationTest : AbstractWebApiTest() {
    @Test
    fun `should return OK when the brand is found`() {
        webClient.get()
            .uri("/api/brands/{brand}", "acme")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo("acme")
            .jsonPath("$.name").isEqualTo("ACME")
            .jsonPath("$.registered_company_name").isEqualTo("Associazione Costruzioni Modellistiche Esatte")
            .jsonPath("$.kind").isEqualTo("INDUSTRIAL")
            .jsonPath("$.status").isEqualTo("ACTIVE")
    }

    @Test
    fun `should return NOT_FOUND when the brand is not found`() {
        webClient.get()
            .uri("/api/brands/{brand}", "not-found")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound
            .expectBody().isEmpty
    }
}
