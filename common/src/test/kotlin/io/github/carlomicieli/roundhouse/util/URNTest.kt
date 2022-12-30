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

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("URN")
class URNTest {
    @Test
    fun `can represent a problem type`() {
        val urn = URN.fromProblemType("blank")
        urn.value shouldBe "trn:problem-type:blank"
    }

    @Test
    fun `can represent a request id`() {
        val urn = URN.fromRequestId("1234")
        urn.value shouldBe "trn:request-id:1234"
    }

    @Test
    fun `can represent an unique identifier`() {
        val id = UUID.randomUUID()
        val urn = URN.fromUUID(id)
        urn.value shouldBe "trn:uuid:$id"
    }

    @Test
    fun `should throw an exception for blank input values`() {
        val exception = shouldThrowExactly<IllegalArgumentException> {
            URN("")
        }
        exception.message shouldBe "URN value cannot be blank"
    }
}
