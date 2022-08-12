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
package com.trenako

import com.trenako.problems.ProblemDetailsGenerator
import com.trenako.util.RandomUuidSource
import com.trenako.util.UuidSource
import com.trenako.web.api.catalog.brands.Brands
import org.springframework.context.support.beans
import java.time.Clock

object ApplicationConfig {
    val common = listOf(
        commonBeans
    )

    val catalog = listOf(
        Brands.beans
    )
}

val commonBeans = beans {
    bean<Clock>() { Clock.systemDefaultZone() }
    bean<UuidSource>() { RandomUuidSource }
    bean<ProblemDetailsGenerator>()
}
