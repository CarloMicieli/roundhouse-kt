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
import io.github.carlomicieli.roundhouse.catalog.brands.createbrands.CreateBrand
import io.github.carlomicieli.roundhouse.catalog.brands.createbrands.CreateBrandError
import io.github.carlomicieli.roundhouse.catalog.brands.createbrands.CreateBrandUseCase
import io.github.carlomicieli.roundhouse.web.api.usecases.UseCaseHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.awaitBodyOrNull

class CreateBrandHandler(
    override val useCase: CreateBrandUseCase,
    override val presenter: CreateBrandPresenter,
    override val logger: Logger = CreateBrandHandler.logger
) : UseCaseHandler<CreateBrand, BrandCreated, CreateBrandError> {
    override suspend fun extractInput(serverRequest: ServerRequest): CreateBrand? = serverRequest.awaitBodyOrNull()

    companion object {
        private val logger = LoggerFactory.getLogger("CreateBrandHandler")
    }
}
