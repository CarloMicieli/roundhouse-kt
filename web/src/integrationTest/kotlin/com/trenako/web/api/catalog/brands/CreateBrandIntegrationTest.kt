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
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

private const val POST_BRANDS_ENDPOINT = "/api/brands"

@DisplayName("POST /api/brands")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@Testcontainers
class CreateBrandIntegrationTest {

    companion object {
        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:14.2-alpine")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.liquibase.enabled") { "true" }
            registry.add("spring.liquibase.url", postgresContainer::getJdbcUrl)
            registry.add("spring.liquibase.user", postgresContainer::getUsername)
            registry.add("spring.liquibase.password", postgresContainer::getPassword)

            val url = postgresContainer.jdbcUrl.replace("jdbc:", "r2dbc:")
            registry.add("spring.r2dbc.url") { url }
            registry.add("spring.r2dbc.username", postgresContainer::getUsername)
            registry.add("spring.r2dbc.password", postgresContainer::getPassword)
        }
    }

    @Autowired
    lateinit var webClient: WebTestClient

    @Test
    fun `should return UNPROCESSABLE_ENTITY when the request body is empty`() {
        webClient.post()
            .uri(POST_BRANDS_ENDPOINT)
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
            .uri(POST_BRANDS_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newBrand)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
            .expectBody()
            .jsonPath("$.detail")
            .isEqualTo("Fields validation failed for this request. Check them before you try again.")
            .jsonPath("$.instance").isNotEmpty
            .jsonPath("$.timestamp").isNotEmpty
            .jsonPath("$.title").isEqualTo("Invalid request")
            .jsonPath("$.type").isEqualTo("trn:problem-type:bad-request")
            .jsonPath("$.fields.name").isEqualTo("size must be between 3 and 100")
    }

    @Test
    fun `should return CONFLICT when a brand with the same name already exists`() {
        val name = UUID.randomUUID().toString()
        val newBrand = RequestBody(
            name = name,
            kind = "INDUSTRIAL"
        )

        webClient.post()
            .uri(POST_BRANDS_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newBrand)
            .exchange()
            .expectStatus().isCreated

        webClient.post()
            .uri(POST_BRANDS_ENDPOINT)
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
            .jsonPath("$.fields.name").isEqualTo(name)
    }

    @Test
    fun `should create a new brand`() {
        val name = UUID.randomUUID().toString()
        val newBrand = RequestBody(
            name = name,
            groupName = "group",
            description = "Description goes here",
            registeredCompanyName = "Company & co",
            kind = "INDUSTRIAL",
            contactInfo = ContactInfo(
                email = "mail@mail.com",
                websiteUrl = "https://www.website.com",
                phoneNumber = "555 1234"
            ),
            status = "ACTIVE"
        )

        webClient.post()
            .uri(POST_BRANDS_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(newBrand)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals("Location", "/api/brands/$name")
    }

    data class ContactInfo(val email: String?, val websiteUrl: String?, val phoneNumber: String?)

    data class RequestBody(
        val name: String = "",
        val kind: String = "",
        val groupName: String? = null,
        val registeredCompanyName: String? = null,
        val description: String? = null,
        val contactInfo: ContactInfo? = null,
        val status: String? = null
    )
}
