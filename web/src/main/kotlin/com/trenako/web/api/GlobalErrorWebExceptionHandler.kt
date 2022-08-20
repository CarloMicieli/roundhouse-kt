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
import com.trenako.web.api.usecases.toServerResponseMono
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

@Component
@Order(-2)
class GlobalErrorWebExceptionHandler(
    errorAttributes: ErrorAttributes,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(errorAttributes, WebProperties.Resources(), applicationContext) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(GlobalErrorWebExceptionHandler::class.java)
    }

    init {
        super.setMessageWriters(serverCodecConfigurer.writers)
        super.setMessageReaders(serverCodecConfigurer.readers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse)
    }

    private fun renderErrorResponse(request: ServerRequest): Mono<ServerResponse> {
        val errorPropertiesMap = getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION))

        log.debug("{}", errorPropertiesMap)
        val ex = getError(request)
        log.error("An error has occurred", ex)

        val status = errorPropertiesMap.getOrDefault("status", HttpStatus.INTERNAL_SERVER_ERROR.value()) as Int
        return if (status == 404) {
            ServerResponse.notFound().build()
        } else {
            val problemDetails = problemDetailsFrom(errorPropertiesMap)
            problemDetails.toServerResponseMono()
        }
    }

    /** Creates a new {@code ProblemDetails} from the request error properties Map */
    private fun problemDetailsFrom(errorProperties: Map<String, Any>): ProblemDetails {
        val timestamp = errorProperties["timestamp"] as Date
        val requestId = errorProperties.getOrDefault("requestId", "0").toString()

        return ProblemDetails(
            type = URN.fromProblemType("internal-server-error"),
            title = "Internal Server Error",
            detail = "An error has occurred",
            category = ProblemCategory.Error,
            timestamp = timestamp.toLocalDateTime(),
            instance = URN.fromRequestId(requestId),
            fields = mapOf()
        )
    }

    private fun Date.toLocalDateTime(): LocalDateTime {
        return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}
