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
package io.github.carlomicieli.roundhouse.catalog.brands

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BrandId")
class BrandIdTest {
    @Test
    fun `is created from a string value`() {
        val id = io.github.carlomicieli.roundhouse.catalog.brands.BrandId.of("my brand")
        id.toString() shouldBe "my-brand"
    }

    @Test
    fun `implements equality`() {
        val id1 = io.github.carlomicieli.roundhouse.catalog.brands.BrandId.of("my brand")
        val id2 = io.github.carlomicieli.roundhouse.catalog.brands.BrandId.of("my brand")

        id1 shouldBe id2
    }

    @Test
    fun `can be compared`() {
        val id1 = io.github.carlomicieli.roundhouse.catalog.brands.BrandId.of("first")
        val id2 = io.github.carlomicieli.roundhouse.catalog.brands.BrandId.of("second")

        (id1 > id2) shouldBe false
        (id1 < id2) shouldBe true
    }

    @Test
    fun `should throw an IllegalArgumentException when the input is blank`() {
        val exception = shouldThrowExactly<IllegalArgumentException> {
            io.github.carlomicieli.roundhouse.catalog.brands.BrandId.of("")
        }
        exception.message shouldBe "Brand id cannot be blank"
    }
}
