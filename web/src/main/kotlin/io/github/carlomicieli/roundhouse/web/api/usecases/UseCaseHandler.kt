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
import org.slf4j.Logger
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerRequest.Headers
import org.springframework.web.reactive.function.server.ServerResponse

/**
 * A basic interface for use case handlers
 * @param I the use case input type
 * @param O the use case output type
 * @param E the use case error type
 */
@Transactional
interface UseCaseHandler<I, O, E> {
    val useCase: UseCase<I, O, E>
    val presenter: UseCaseResultPresenter<O, E>

    val logger: Logger

    /**
     * Handle the server request with the {@code UseCase} associated with this handler.
     * @param serverRequest the server request
     * @return a server response
     */
    suspend fun handle(serverRequest: ServerRequest): ServerResponse {
        val input = extractInput(serverRequest)

        return if (input == null) {
            presenter.toEmptyRequestResponse()
        } else {
            val result = useCase.execute(input)
            val logEntry = LogEntry(input, result, serverRequest.headers())
            logEntry.logTo(logger)
            presenter.toServerResponse(result)
        }
    }

    /**
     * This method is used to extract the input from the server request.
     *
     * It is not expected for this to throw any exception, if the extraction is not possible
     * then the method should simply return {@code null}.
     *
     * @param serverRequest the server request
     * @return the use case input extracted from the server request, or {@code null} if the extraction is not possible
     */
    suspend fun extractInput(serverRequest: ServerRequest): I?
}

data class LogEntry<I, O>(val request: I, val response: O, val headers: Headers) {
    fun logTo(logger: Logger) {
        val log =
            mapOf(
                "request" to request,
                "response" to response,
                "headers" to headers.asHttpHeaders().toMap()
            )

        logger.info("{}", log)
    }
}
