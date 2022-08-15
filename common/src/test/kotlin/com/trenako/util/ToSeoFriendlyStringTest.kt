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
package com.trenako.util

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("toSeoFriendlyString")
class ToSeoFriendlyStringTest {
    @Test
    fun `should encode whitespaces`() {
        val encoded = "Time is an illusion".toSeoFriendlyString()
        encoded shouldBe "time-is-an-illusion"
    }

    @Test
    fun `should encode punctuation signs`() {
        val encoded = "Time; is an: illusion.".toSeoFriendlyString()
        encoded shouldBe "time-is-an-illusion"
    }

    @Test
    fun `should encode non Latin characters`() {
        val encoded = "Timè is àn illusiòn.".toSeoFriendlyString()
        encoded shouldBe "time-is-an-illusion"
    }

    @Test
    fun `should encode uppercase characters`() {
        val encoded = "TIME".toSeoFriendlyString()
        encoded shouldBe "time"
    }

    @Test
    fun `should encode numbers leaving them unchanged`() {
        val encoded = "123456".toSeoFriendlyString()
        encoded shouldBe "123456"
    }

    @Test
    fun `should encode an already encoded values leaving them unchanged`() {
        val encoded = "Time; is an: illusion.".toSeoFriendlyString().toSeoFriendlyString()
        encoded shouldBe "time-is-an-illusion"
    }
}
