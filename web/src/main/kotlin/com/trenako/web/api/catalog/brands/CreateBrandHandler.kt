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

import com.trenako.catalog.brands.createbrands.CreateBrand
import com.trenako.problems.ProblemDetailsGenerator
import com.trenako.validation.Validated
import com.trenako.validation.inputValidator
import com.trenako.web.api.toServerResponse
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.buildAndAwait
import java.net.URI
import javax.validation.Validator

class CreateBrandHandler(private val problemDetailsGenerator: ProblemDetailsGenerator, private val validator: Validator) {
    suspend fun handle(serverRequest: ServerRequest): ServerResponse {
        val createBrand = serverRequest.awaitBodyOrNull<CreateBrand>()
        return if (createBrand == null) {
            problemDetailsGenerator.unprocessableEntity("Request body is empty").toServerResponse()
        } else {
            val inputValidator = validator.inputValidator<CreateBrand>()
            when (val result = inputValidator.validate(createBrand)) {
                is Validated.Valid -> ServerResponse.created(URI("/api/brands/acme")).buildAndAwait()
                is Validated.Invalid -> problemDetailsGenerator.invalidRequest(result.errors).toServerResponse()
            }
        }
    }
}
