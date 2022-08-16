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

import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.util.UUID

private const val POST_BRANDS_ENDPOINT = "/api/brands"

class CreateBrandIntegrationTest : AbstractWebApiTest() {

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
            address = Address(
                streetAddress = "22 Acacia Avenue",
                extendedAddress = "Apt. 123",
                city = "London",
                postalCode = "1H2 H88",
                countryCode = "GB",
                region = "Sussex"
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

    data class Address(
        val streetAddress: String,
        val extendedAddress: String?,
        val city: String,
        val region: String?,
        val postalCode: String?,
        val countryCode: String
    )

    data class RequestBody(
        val name: String = "",
        val kind: String = "",
        val groupName: String? = null,
        val registeredCompanyName: String? = null,
        val description: String? = null,
        val contactInfo: ContactInfo? = null,
        val address: Address? = null,
        val status: String? = null
    )
}
