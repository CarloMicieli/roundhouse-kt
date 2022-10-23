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
package com.trenako.web.api.catalog.scales

import com.trenako.catalog.scales.createscales.CreateScaleUseCase
import com.trenako.catalog.scales.getscalebyid.GetScaleByIdQuery
import com.trenako.catalog.scales.getscales.GetScalesQuery
import com.trenako.infrastructure.persistence.catalog.ScalesRepository
import org.springframework.context.support.beans
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

object Scales {
    val beans = beans {
        bean<CreateScaleHandler>()
        bean<CreateScaleUseCase>()
        bean<CreateScalePresenter>()

        bean<GetScaleByIdHandler>()
        bean<GetScaleByIdQuery>()
        bean<GetScaleByIdPresenter>()

        bean<GetScalesHandler>()
        bean<GetScalesQuery>()
        bean<GetScalesPresenter>()

        bean<ScalesRepository>()

        bean {
            val createScaleHandler = ref<CreateScaleHandler>()
            val getScaleByIdHandler = ref<GetScaleByIdHandler>()
            val getScalesHandler = ref<GetScalesHandler>()
            routes(createScaleHandler, getScaleByIdHandler, getScalesHandler)
        }
    }

    internal fun routes(
        createScaleHandler: CreateScaleHandler,
        getScaleByIdHandler: GetScaleByIdHandler,
        getScalesHandler: GetScalesHandler
    ): RouterFunction<ServerResponse> = coRouter {
        "/api/scales".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                POST("", createScaleHandler::handle)
            }

            GET("", getScalesHandler::handle)
            GET("{scale}", getScaleByIdHandler::handle)
        }
    }
}
