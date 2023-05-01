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
package io.github.carlomicieli.roundhouse.web.api.usecases

import io.github.carlomicieli.roundhouse.problems.ProblemDetails
import io.github.carlomicieli.roundhouse.problems.ProblemDetailsGenerator
import io.github.carlomicieli.roundhouse.usecases.UseCaseResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.time.LocalDateTime
import java.util.UUID

@TestInstance(Lifecycle.PER_CLASS)
class UseCaseResultPresenterTest {

    private val useCaseResultPresenter: UseCaseResultPresenter<String, String> =
        object : UseCaseResultPresenter<String, String> {
            override suspend fun outputToResponse(output: String): ServerResponse =
                ServerResponse.ok().bodyValueAndAwait(output)

            override suspend fun errorToProblemDetails(error: String): ProblemDetails =
                ProblemDetailsGenerator.fixed(LocalDateTime.now(), UUID.randomUUID()).unprocessableEntity(error)

            override val problemDetailsGenerator: ProblemDetailsGenerator
                get() = ProblemDetailsGenerator.default()
        }

    @Test
    fun `should produce the server response for use case outputs`() = runBlocking {
        val serverResponse = useCaseResultPresenter.toServerResponse(UseCaseResult.withOutput("ok"))
        serverResponse.statusCode() shouldBe HttpStatus.OK
    }

    @Test
    fun `should produce the server response for use case errors`() = runBlocking {
        val serverResponse = useCaseResultPresenter.toServerResponse(UseCaseResult.withError("error"))
        serverResponse.statusCode() shouldBe HttpStatus.UNPROCESSABLE_ENTITY
        serverResponse.headers().contentType shouldBe MediaType.APPLICATION_PROBLEM_JSON
    }
}
