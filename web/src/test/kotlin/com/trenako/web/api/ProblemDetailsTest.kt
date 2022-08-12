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
package com.trenako.web.api

import com.trenako.problems.ProblemCategory
import com.trenako.problems.ProblemDetails
import com.trenako.util.URN
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.time.LocalDateTime

@DisplayName("ProblemDetails")
class ProblemDetailsTest {
    @Test
    fun `should produce an unprocessable entity response`() = runBlocking {
        val problemDetails = ProblemDetails(
            type = URN("type"),
            title = "title",
            detail = "detail",
            category = ProblemCategory.UnprocessableEntity,
            timestamp = LocalDateTime.now(),
            instance = URN("instance"),
            fields = mapOf("field1" to "value1")
        )

        val serverResponse = problemDetails.toServerResponse()
        serverResponse.statusCode() shouldBe HttpStatus.UNPROCESSABLE_ENTITY
        serverResponse.headers().contentType shouldBe MediaType.APPLICATION_PROBLEM_JSON
    }

    @Test
    fun `should produce a bad request response`() = runBlocking {
        val problemDetails = ProblemDetails(
            type = URN("type"),
            title = "title",
            detail = "detail",
            category = ProblemCategory.InvalidRequest,
            timestamp = LocalDateTime.now(),
            instance = URN("instance"),
            fields = mapOf("field1" to "value1")
        )

        val serverResponse = problemDetails.toServerResponse()
        serverResponse.statusCode() shouldBe HttpStatus.BAD_REQUEST
        serverResponse.headers().contentType shouldBe MediaType.APPLICATION_PROBLEM_JSON
    }
}
