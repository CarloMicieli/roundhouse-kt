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
import com.trenako.validation.ValidationError
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import java.time.LocalDateTime
import java.util.*

@DisplayName("ProblemDetailsGenerator")
@TestInstance(Lifecycle.PER_CLASS)
class ProblemDetailsGeneratorTest {
    private val id = UUID.fromString("420c52bd-22f9-4772-88c5-361cbe6dbaaf")
    private val now = LocalDateTime.now()
    private val problemDetailsGenerator = ProblemDetailsGenerator.fixed(now, id)

    @Test
    fun `should generate problem details for unprocessable entities`() {
        val problemDetails = problemDetailsGenerator.unprocessableEntity("Request body is empty")
        problemDetails.category shouldBe ProblemCategory.UnprocessableEntity
        problemDetails.detail shouldBe "Request body is empty"
        problemDetails.fields shouldBe mapOf()
        problemDetails.instance shouldBe URN("trn:uuid:420c52bd-22f9-4772-88c5-361cbe6dbaaf")
        problemDetails.timestamp shouldBe now
        problemDetails.title shouldBe "Unprocessable entity"
        problemDetails.type shouldBe URN("trn:problem-type:unprocessable-entity")
    }

    @Test
    fun `should generate problem details for invalid requests`() {
        val errors = listOf(ValidationError("field1", "errorMessage1"))
        val problemDetails = problemDetailsGenerator.invalidRequest(errors)
        problemDetails.category shouldBe ProblemCategory.InvalidRequest
        problemDetails.detail shouldBe "Fields validation failed for this request. Check them before you try again."
        problemDetails.fields shouldBe mapOf("field1" to "errorMessage1")
        problemDetails.instance shouldBe URN("trn:uuid:420c52bd-22f9-4772-88c5-361cbe6dbaaf")
        problemDetails.timestamp shouldBe now
        problemDetails.title shouldBe "Invalid request"
        problemDetails.type shouldBe URN("trn:problem-type:bad-request")
    }
}
