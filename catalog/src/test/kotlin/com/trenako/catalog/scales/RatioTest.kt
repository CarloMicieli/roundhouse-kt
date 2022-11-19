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
package com.trenako.catalog.scales

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Ratio")
class RatioTest {
    @Test
    fun `should create new scale Ratios`() {
        val halfZeroRatio = Ratio.of(87.0)
        val zeroRatio = Ratio.of(43.5)

        halfZeroRatio.toString() shouldBe "1:87"
        zeroRatio.toString() shouldBe "1:43.5"
    }

    @Test
    fun `should try to create new scale Ratios`() {
        val halfZeroRatio = Ratio.tryCreate(87.0)
        val invalidRatio = Ratio.tryCreate(-1.0)

        halfZeroRatio.isSuccess shouldBe true
        halfZeroRatio.getOrNull()?.toString() shouldBe "1:87"
        invalidRatio.isFailure shouldBe true
    }

    @Test
    fun `should allow only positive ratios`() {
        val ex = shouldThrowExactly<IllegalArgumentException> {
            Ratio.of(-1.0)
        }
        ex.message shouldBe "ratio value must be positive"
    }

    @Test
    fun `should not allow zero ratios`() {
        val ex = shouldThrowExactly<IllegalArgumentException> {
            Ratio.of(0.0)
        }
        ex.message shouldBe "ratio value must be positive"
    }

    @Test
    fun `should check ratio equality`() {
        val halfZeroRatio = Ratio.of(87.0)
        val zeroRatio = Ratio.of(43.5)

        (halfZeroRatio == zeroRatio) shouldBe false
        (halfZeroRatio != zeroRatio) shouldBe true
    }

    @Test
    fun `should compare two ratio values`() {
        val halfZeroRatio = Ratio.of(87.0)
        val zeroRatio = Ratio.of(43.5)

        (halfZeroRatio < zeroRatio) shouldBe true
        (halfZeroRatio > zeroRatio) shouldBe false
    }

    @Test
    fun `should convert from float values to ratios`() {
        val ratio1: Ratio? = 43.5f.toRatio()
        val ratio2: Ratio? = (-1f).toRatio()

        ratio1?.toString() shouldBe "1:43.5"
        ratio2 shouldBe null
    }

    @Test
    fun `should convert from double values to ratios`() {
        val ratio1: Ratio? = 43.5.toRatio()
        val ratio2: Ratio? = (-1.0).toRatio()

        ratio1?.toString() shouldBe "1:43.5"
        ratio2 shouldBe null
    }
}
