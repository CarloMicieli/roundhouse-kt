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

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@DisplayName("POST /api/brands")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
class CreateBrandIntegrationTest() {

    @Autowired
    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var context: ApplicationContext

    @Test
    fun `should return UNPROCESSABLE_ENTITY when the request body is empty`() {
        webClient.post()
            .uri("/api/brands")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectBody()
            .jsonPath("$.detail").isEqualTo("Request body is empty")
            .jsonPath("$.instance").isNotEmpty
            .jsonPath("$.timestamp").isNotEmpty
            .jsonPath("$.title").isEqualTo("Unprocessable entity")
            .jsonPath("$.type").isEqualTo("trn:problem-type:unprocessable-entity")
    }

    @Test
    fun `should return BAD_REQUEST when the request body is invalid`() {
        val newBrand = RequestBody(name = "a")

        webClient.post()
            .uri("/api/brands")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newBrand)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
            .expectBody()
            .jsonPath("$.detail").isEqualTo("Fields validation failed for this request. Check them before you try again.")
            .jsonPath("$.instance").isNotEmpty
            .jsonPath("$.timestamp").isNotEmpty
            .jsonPath("$.title").isEqualTo("Invalid request")
            .jsonPath("$.type").isEqualTo("trn:problem-type:bad-request")
            .jsonPath("$.fields.name").isEqualTo("size must be between 3 and 100")
    }

    @Test
    fun `should return CONFLICT when a brand with the same name already exists`() {
        val newBrand = RequestBody(name = "roco")

        webClient.post()
            .uri("/api/brands")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newBrand)
            .exchange()
            .expectStatus().isCreated

        webClient.post()
            .uri("/api/brands")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newBrand)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.CONFLICT)
            .expectBody()
            .jsonPath("$.detail").isEqualTo("Brand already exists")
            .jsonPath("$.instance").isNotEmpty
            .jsonPath("$.timestamp").isNotEmpty
            .jsonPath("$.title").isEqualTo("Already exists")
            .jsonPath("$.type").isEqualTo("trn:problem-type:already-exists")
            .jsonPath("$.fields.name").isEqualTo("roco")
    }

    @Test
    fun `should create a new brand`() {
        val newBrand = RequestBody(name = "acme")

        webClient.post()
            .uri("/api/brands")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newBrand)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals("Location", "/api/brands/acme")
    }

    data class RequestBody(val name: String = "")
}
