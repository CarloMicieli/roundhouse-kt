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
package io.github.carlomicieli.roundhouse

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.github.carlomicieli.roundhouse.infrastructure.persistence.catalog.seeding.CatalogSeeding
import io.github.carlomicieli.roundhouse.problems.ProblemDetailsGenerator
import io.github.carlomicieli.roundhouse.util.RandomUuidSource
import io.github.carlomicieli.roundhouse.util.UuidSource
import io.github.carlomicieli.roundhouse.web.api.catalog.brands.Brands
import io.github.carlomicieli.roundhouse.web.api.catalog.scales.Scales
import io.github.carlomicieli.roundhouse.web.api.customSerializerModule
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.context.support.beans
import java.time.Clock

object ApplicationConfig {
    val common =
        listOf(
            commonBeans
        )

    val catalog =
        listOf(
            Brands.beans,
            Scales.beans
        )
}

val commonBeans =
    beans {
        bean<Clock> { Clock.systemDefaultZone() }
        bean<UuidSource> { RandomUuidSource }
        bean<ProblemDetailsGenerator>()
        bean<ObjectMapper> {
            ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .registerModule(customSerializerModule())
                .registerModule(JavaTimeModule())
                .registerModule(KotlinModule.Builder().build())
        }

        profile("local || it") {
            bean<CatalogSeeding>()
            bean {
                CommandLineRunner {
                    runBlocking {
                        val catalogSeeding = ref<CatalogSeeding>()
                        catalogSeeding.seed()
                    }
                }
            }
        }
    }
