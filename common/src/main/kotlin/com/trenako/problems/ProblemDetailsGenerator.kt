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

import com.trenako.util.FixedUuidSource
import com.trenako.util.RandomUuidSource
import com.trenako.util.URN
import com.trenako.util.UuidSource
import com.trenako.validation.ValidationError
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

class ProblemDetailsGenerator(private val clock: Clock, private val idSource: UuidSource) {

    /**
     * Create a {@code ProblemDetails} instance for unprocessable entity
     * @param message the message to detail this problem
     * @return a {@code ProblemDetails} instance
     */
    fun unprocessableEntity(message: String) =
        ProblemDetails(
            type = URN.fromProblemType("unprocessable-entity"),
            title = "Unprocessable entity",
            detail = message,
            category = ProblemCategory.UnprocessableEntity,
            timestamp = LocalDateTime.now(clock),
            instance = URN.fromUUID(idSource.newId()),
            fields = mapOf()
        )

    /**
     * Create a {@code ProblemDetails} instance for invalid request
     * @param errors the validation errors list
     * @return a {@code ProblemDetails} instance
     */
    fun invalidRequest(errors: List<ValidationError>) =
        ProblemDetails(
            type = URN.fromProblemType("bad-request"),
            title = "Invalid request",
            detail = "Fields validation failed for this request. Check them before you try again.",
            category = ProblemCategory.InvalidRequest,
            timestamp = LocalDateTime.now(clock),
            instance = URN.fromUUID(idSource.newId()),
            fields = errors.associate { it.fieldName to it.errorMessage }
        )

    /**
     * Create a {@code ProblemDetails} instance for duplicated records
     * @param message the general message
     * @param fields the fields to give additional information
     * @return a {@code ProblemDetails} instance
     */
    fun alreadyExists(message: String, fields: Map<String, String>) =
        ProblemDetails(
            type = URN.fromProblemType("already-exists"),
            title = "Already exists",
            detail = message,
            category = ProblemCategory.AlreadyExists,
            timestamp = LocalDateTime.now(clock),
            instance = URN.fromUUID(idSource.newId()),
            fields = fields
        )

    companion object {
        /**
         * Create a default {@code ProblemDetailsGenerator} with a random UUID generator and using the
         * system default clock.
         */
        fun default(): ProblemDetailsGenerator = ProblemDetailsGenerator(Clock.systemDefaultZone(), RandomUuidSource)

        /**
         * Create a {@code ProblemDetailsGenerator} which generate problem details with a fixed timestamps and ids
         */
        fun fixed(now: LocalDateTime, id: UUID): ProblemDetailsGenerator {
            val instant = ZonedDateTime.of(now, ZoneId.systemDefault()).toInstant()
            val clock = Clock.fixed(instant, ZoneId.systemDefault())
            val idSource = FixedUuidSource(id)
            return ProblemDetailsGenerator(clock, idSource)
        }
    }
}
