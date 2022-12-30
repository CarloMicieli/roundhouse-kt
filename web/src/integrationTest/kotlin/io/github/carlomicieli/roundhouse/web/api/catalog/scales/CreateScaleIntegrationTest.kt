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

import io.github.carlomicieli.roundhouse.AbstractWebApiTest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.math.BigDecimal

private const val POST_SCALES_ENDPOINT = "/api/scales"

class CreateScaleIntegrationTest : AbstractWebApiTest() {
    @Test
    fun `should return UNPROCESSABLE_ENTITY when the request body is empty`() {
        webClient.post()
            .uri(POST_SCALES_ENDPOINT)
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
        val newScale = RequestBody(name = "12345678901")

        webClient.post()
            .uri(POST_SCALES_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newScale)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
            .expectBody()
            .jsonPath("$.detail")
            .isEqualTo("Fields validation failed for this request. Check them before you try again.")
            .jsonPath("$.instance").isNotEmpty
            .jsonPath("$.timestamp").isNotEmpty
            .jsonPath("$.title").isEqualTo("Invalid request")
            .jsonPath("$.type").isEqualTo("trn:problem-type:bad-request")
            .jsonPath("$.fields.name").isEqualTo("size must be between 1 and 10")
    }

    @Test
    fun `should return CONFLICT when a scale with the same name already exists`() {
        val name = "1234567890"
        val newScale = RequestBody(
            name = name,
            ratio = BigDecimal.valueOf(87),
            gauge = Gauge("STANDARD", BigDecimal.valueOf(16.5))
        )

        webClient.post()
            .uri(POST_SCALES_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newScale)
            .exchange()
            .expectStatus().isCreated

        webClient.post()
            .uri(POST_SCALES_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newScale)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.CONFLICT)
            .expectBody()
            .jsonPath("$.detail").isEqualTo("Scale already exists")
            .jsonPath("$.instance").isNotEmpty
            .jsonPath("$.timestamp").isNotEmpty
            .jsonPath("$.title").isEqualTo("Already exists")
            .jsonPath("$.type").isEqualTo("trn:problem-type:already-exists")
            .jsonPath("$.fields.name").isEqualTo(name)
    }

    @Test
    fun `should create a new scale`() {
        val name = "nnnnn"
        val newScale = RequestBody(
            name = name,
            ratio = BigDecimal.valueOf(160),
            gauge = Gauge("STANDARD", BigDecimal.valueOf(9)),
            description = "Most famous scale",
            standards = setOf("NEM")
        )

        webClient.post()
            .uri(POST_SCALES_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newScale)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals("Location", "/api/scales/$name")
    }

    data class Gauge(val track_gauge: String, val millimeters: BigDecimal? = null, val inches: BigDecimal? = null)

    data class RequestBody(
        val name: String = "",
        val ratio: BigDecimal = BigDecimal.ZERO,
        val gauge: Gauge = Gauge("STANDARD"),
        val description: String? = null,
        val standards: Set<String>? = null
    )
}
