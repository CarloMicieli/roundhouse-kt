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
package io.github.carlomicieli.roundhouse.web.api.catalog.brands

import io.github.carlomicieli.roundhouse.catalog.brands.createbrands.BrandCreated
import io.github.carlomicieli.roundhouse.catalog.brands.createbrands.CreateBrandError
import io.github.carlomicieli.roundhouse.problems.ProblemDetails
import io.github.carlomicieli.roundhouse.problems.ProblemDetailsGenerator
import io.github.carlomicieli.roundhouse.web.api.usecases.UseCaseResultPresenter
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait
import java.net.URI

class CreateBrandPresenter(override val problemDetailsGenerator: ProblemDetailsGenerator) :
    UseCaseResultPresenter<BrandCreated, CreateBrandError> {
    override suspend fun outputToResponse(output: BrandCreated): ServerResponse {
        val location = URI("/api/brands/${output.id}")
        return ServerResponse.created(location).buildAndAwait()
    }

    override suspend fun errorToProblemDetails(error: CreateBrandError): ProblemDetails {
        return when (error) {
            is CreateBrandError.GenericError -> problemDetailsGenerator.error(error.ex.message)
            is CreateBrandError.BrandAlreadyExists ->
                problemDetailsGenerator.alreadyExists(
                    "Brand already exists",
                    mapOf("name" to error.name)
                )

            is CreateBrandError.InvalidRequest -> problemDetailsGenerator.invalidRequest(error.errors)
        }
    }
}
