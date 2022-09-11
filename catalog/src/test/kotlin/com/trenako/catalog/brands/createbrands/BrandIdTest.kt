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
package com.trenako.catalog.brands.createbrands

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("BrandId")
class BrandIdTest {
    @Test
    fun `should compare two Brand ids`() {
        val id1 = BrandId.of("brand-id1")
        val id2 = BrandId.of("brand-id2")

        (id1 < id2) shouldBe true
        (id1 > id2) shouldBe false
        (id2 > id1) shouldBe true
        (id2 < id1) shouldBe false
    }

    @Test
    fun `should produce a String representation for Brand ids`() {
        val id = BrandId.of("brand-id1")
        id.toString() shouldBe "brand-id1"
    }

    @Test
    fun `should throw an exception when the id value is blank`() {
        val ex = shouldThrowExactly<IllegalArgumentException> {
            BrandId.of("")
        }

        ex.message shouldBe "Brand id cannot be blank"
    }
}
