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

import io.github.carlomicieli.roundhouse.catalog.brands.createbrands.CreateBrandUseCase
import io.github.carlomicieli.roundhouse.catalog.brands.getbrandbyid.GetBrandByIdQuery
import io.github.carlomicieli.roundhouse.catalog.brands.getbrands.GetBrandsQuery
import io.github.carlomicieli.roundhouse.infrastructure.persistence.catalog.BrandsRepository
import org.springframework.context.support.beans
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

object Brands {
    val beans =
        beans {
            bean<CreateBrandHandler>()
            bean<CreateBrandUseCase>()
            bean<CreateBrandPresenter>()

            bean<GetBrandByIdHandler>()
            bean<GetBrandByIdQuery>()
            bean<GetBrandByIdPresenter>()

            bean<GetBrandsHandler>()
            bean<GetBrandsQuery>()
            bean<GetBrandsPresenter>()

            bean<BrandsRepository>()

            bean {
                val createBrandHandler = ref<CreateBrandHandler>()
                val getBrandByIdHandler = ref<GetBrandByIdHandler>()
                val getBrandsHandler = ref<GetBrandsHandler>()
                routes(createBrandHandler, getBrandByIdHandler, getBrandsHandler)
            }
        }

    internal fun routes(
        createBrandHandler: CreateBrandHandler,
        getBrandByIdHandler: GetBrandByIdHandler,
        getBrandsHandler: GetBrandsHandler
    ): RouterFunction<ServerResponse> =
        coRouter {
            "/api/brands".nest {
                accept(MediaType.APPLICATION_JSON).nest {
                    POST("", createBrandHandler::handle)
                }

                GET("", getBrandsHandler::handle)
                GET("{brand}", getBrandByIdHandler::handle)
            }
        }
}
