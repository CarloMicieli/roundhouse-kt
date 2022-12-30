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
package io.github.carlomicieli.roundhouse.catalog.scales

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ScaleId")
class ScaleIdTest {
    @Test
    fun `should compare two scale ids`() {
        val id1 = ScaleId.of("scale-id1")
        val id2 = ScaleId.of("scale-id2")

        (id1 < id2) shouldBe true
        (id1 > id2) shouldBe false
        (id2 > id1) shouldBe true
        (id2 < id1) shouldBe false
    }

    @Test
    fun `should produce a String representation for scale ids`() {
        val id = ScaleId.of("scale id1")
        id.toString() shouldBe "scale-id1"
    }

    @Test
    fun `should throw an exception when the id value is blank`() {
        val ex = shouldThrowExactly<IllegalArgumentException> {
            ScaleId.of("")
        }

        ex.message shouldBe "Scale id cannot be blank"
    }
}
