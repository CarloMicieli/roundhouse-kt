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
package com.trenako.problems

import com.trenako.util.URN
import java.time.LocalDateTime

/**
 * From RFC-7807 "problem detail" is a way to carry machine-readable details of errors in an HTTP
 * response to avoid the need to define new error response formats for HTTP APIs.
 */
data class ProblemDetails(
    val type: URN,
    val title: String,
    val detail: String,
    val category: ProblemCategory,
    val timestamp: LocalDateTime,
    val instance: URN,
    val fields: Map<String, String>
)
