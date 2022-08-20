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
package com.trenako.web.api.usecases

import com.trenako.problems.ProblemCategory
import com.trenako.problems.ProblemDetails
import com.trenako.problems.ProblemDetailsGenerator
import com.trenako.usecases.UseCaseResult
import com.trenako.usecases.get
import com.trenako.usecases.map
import com.trenako.usecases.mapError
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.time.LocalDateTime

/**
 * A basic interface for a web api presenter to convert {@code UseCase} results to
 * the most suitable http responses.
 * @param O the output type
 * @param E the error type
 */
interface UseCaseResultPresenter<O, E> {
    val problemDetailsGenerator: ProblemDetailsGenerator

    suspend fun outputToResponse(output: O): ServerResponse

    suspend fun errorToProblemDetails(error: E): ProblemDetails

    suspend fun toServerResponse(result: UseCaseResult<O, E>): ServerResponse = result.map {
        outputToResponse(it)
    }.mapError {
        errorToProblemDetails(it).toServerResponse()
    }.get()

    suspend fun toEmptyRequestResponse(): ServerResponse = problemDetailsGenerator.unprocessableEntity("Request body is empty").toServerResponse()
}

suspend fun ProblemDetails.toServerResponse(): ServerResponse = toServerResponseMono().awaitSingle()

fun ProblemDetails.toServerResponseMono(): Mono<ServerResponse> {
    val mediaType = MediaType.APPLICATION_PROBLEM_JSON
    val httpStatus = categoryToHttpStatus(this.category)

    val body = ProblemDetailsBody(
        detail = this.detail,
        fields = this.fields,
        instance = this.instance.value,
        timestamp = this.timestamp,
        title = this.title,
        type = this.type.value
    )

    return ServerResponse.status(httpStatus).contentType(mediaType).bodyValue(body)
}

private fun categoryToHttpStatus(category: ProblemCategory): HttpStatus = when (category) {
    ProblemCategory.InvalidRequest -> HttpStatus.BAD_REQUEST
    ProblemCategory.UnprocessableEntity -> HttpStatus.UNPROCESSABLE_ENTITY
    ProblemCategory.AlreadyExists -> HttpStatus.CONFLICT
    ProblemCategory.Error -> HttpStatus.INTERNAL_SERVER_ERROR
}

data class ProblemDetailsBody(
    val type: String,
    val title: String,
    val detail: String,
    val timestamp: LocalDateTime,
    val instance: String,
    val fields: Map<String, String>
)
