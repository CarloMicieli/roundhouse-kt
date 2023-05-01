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
package io.github.carlomicieli.roundhouse.util

import java.util.UUID

/**
 * A Uniform Resource Name (URN) is a Uniform Resource Identifier (URI) that uses the urn scheme. URNs are globally
 * unique persistent identifiers assigned within defined namespaces so they will be available for a long period of time,
 * even after the resource which they identify ceases to exist or becomes unavailable.
 *
 * URNs cannot be used to directly locate an item and need not be resolvable, as they are simply templates that
 * another parser may use to find an item.
 */
data class URN(val value: String) {

    init {
        require(value.isNotBlank()) { "URN value cannot be blank" }
    }

    companion object {
        /**
         * Creates a {@code URN} representing a problem type
         * @param problemType a non-empty String to identify a problem type
         */
        fun fromProblemType(problemType: String): URN = URN("trn:problem-type:$problemType")

        /**
         * Creates a {@code URN} representing a unique identifier
         */
        fun fromUUID(id: UUID): URN = URN("trn:uuid:$id")

        fun fromRequestId(requestId: String): URN = URN("trn:request-id:$requestId")
    }
}
