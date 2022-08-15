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
package com.trenako.usecases

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.RuntimeException

@DisplayName("UseCaseResult")
class UseCaseResultTest {
    @Test
    fun `can represent the output value`() {
        val output = UseCaseResult.withOutput(42)
        output.isError() shouldBe false
    }

    @Test
    fun `can represent one error`() {
        val error = UseCaseResult.withError(RuntimeException("some error"))
        error.isError() shouldBe true
    }

    @Test
    fun `map() on use case output values`() {
        val result: UseCaseResult<Int, RuntimeException> = UseCaseResult.withOutput(21)
        val result2 = result.map { it * 2 }
        result2 shouldBe UseCaseResult.withOutput(42)
    }

    @Test
    fun `map() on error results will leave the value unchanged`() {
        val error: UseCaseResult<Int, RuntimeException> = UseCaseResult.withError(RuntimeException("some error"))
        val error2 = error.map { it * 2 }
        error shouldBe error2
    }

    @Test
    fun `mapError() on use case output values`() {
        val result: UseCaseResult<Int, String> = UseCaseResult.withOutput(21)
        val result2 = result.mapError { it + it }
        result shouldBe result2
    }

    @Test
    fun `mapError() on error use case results`() {
        val result: UseCaseResult<Int, String> = UseCaseResult.withError("some")
        val result2 = result.mapError { it + it }
        result2 shouldBe UseCaseResult.withError("somesome")
    }

    @Test
    fun `flatMap() on use case output values`() {
        val result: UseCaseResult<Int, RuntimeException> = UseCaseResult.withOutput(21)
        val result2 = result.flatMap { UseCaseResult.withOutput(it * 2) }
        result2 shouldBe UseCaseResult.withOutput(42)
    }

    @Test
    fun `flatMap() on error results will leave the value unchanged`() {
        val result: UseCaseResult<Int, RuntimeException> = UseCaseResult.withError(RuntimeException("some error"))
        val result2 = result.flatMap { UseCaseResult.withOutput(it * 2) }
        result shouldBe result2
    }

    @Test
    fun `get() extract the value when output and error have the same type`() {
        val result1: UseCaseResult<Int, Long> = UseCaseResult.withOutput(42)
        val result2: UseCaseResult<Int, Long> = UseCaseResult.withError(42L)

        val v1 = result1.map { it.toString() }.mapError { it.toString() }.get()
        val v2 = result2.map { it.toString() }.mapError { it.toString() }.get()

        v1 shouldBe "42"
        v2 shouldBe "42"
    }
}
