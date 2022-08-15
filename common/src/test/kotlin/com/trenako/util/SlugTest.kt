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

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Slug")
class SlugTest {
    @Test
    fun `should create new values from Strings`() {
        val slug = Slug("Time is an illusion")
        slug.toString() shouldBe "time-is-an-illusion"
    }

    @Test
    fun `should throw an IllegalArgumentException if the input String is blank`() {
        val exception = shouldThrowExactly<IllegalArgumentException> { Slug("") }
        exception shouldHaveMessage "Slug value cannot be blank"
    }

    @Test
    fun `should implement the equality check`() {
        val slug1 = Slug("Hello world")
        val slug2 = Slug("hello world")
        val slug3 = Slug("world hello")
        (slug1 == slug2) shouldBe true
        (slug1 == slug3) shouldBe false
    }

    @Test
    fun `should implement hash code`() {
        val slug1 = Slug("Hello world")
        val slug2 = Slug("hello world")
        slug1.hashCode() shouldBeEqualComparingTo slug2.hashCode()
    }
}
