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

import io.github.carlomicieli.roundhouse.usecases.UseCase
import io.github.carlomicieli.roundhouse.usecases.UseCaseResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

@Disabled
class UseCaseHandlerTest {
    @Test
    fun `should handle successful use case results`() =
        runTest {
            val input = 42
            val result = UseCaseResult.withOutput("ok")

            val useCase =
                mock<UseCase<Int, String, Exception>> {
                    onBlocking { execute(input) } doReturn result
                }

            val presenter =
                mock<UseCaseResultPresenter<String, Exception>> {
                    onBlocking { toServerResponse(result) } doReturn ServerResponse.ok().bodyValueAndAwait("ok")
                }

            val request = mock<ServerRequest>()
            val useCaseHandler = TestUseCaseHandler(useCase, presenter, input)

            val response = useCaseHandler.handle(request)
            response.statusCode() shouldBe HttpStatus.OK
        }

    @Test
    fun `should handle error use case results`() =
        runTest {
            val input = 42
            val error = java.lang.Exception("ops")
            val result = UseCaseResult.withError(error)

            val useCase =
                mock<UseCase<Int, String, Exception>> {
                    onBlocking { execute(input) } doReturn result
                }

            val presenter =
                mock<UseCaseResultPresenter<String, Exception>> {
                    onBlocking {
                        toServerResponse(result)
                    } doReturn ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).buildAndAwait()
                }

            val request = mock<ServerRequest>()
            val useCaseHandler = TestUseCaseHandler(useCase, presenter, input)

            val response = useCaseHandler.handle(request)
            response.statusCode() shouldBe HttpStatus.INTERNAL_SERVER_ERROR
        }
}

class TestUseCaseHandler(
    override val useCase: UseCase<Int, String, Exception>,
    override val presenter: UseCaseResultPresenter<String, Exception>,
    private val expectedInput: Int? = null
) : UseCaseHandler<Int, String, Exception> {
    override suspend fun extractInput(serverRequest: ServerRequest): Int? = expectedInput

    override val logger: Logger
        get() = LoggerFactory.getLogger("test")
}
