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
package com.trenako.contact

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.net.URI
import java.net.URISyntaxException

@DisplayName("Website URL")
class WebsiteUrlTest {
    @Test
    fun `is created from valid urls`() {
        val result = WebsiteUrl.tryCreate("http://www.google.com")
        result.isSuccess shouldBe true
        result.getOrNull() shouldBe WebsiteUrl(URI("http://www.google.com"))
    }

    @Test
    fun `returns a Failure when the input is not a valid url`() {
        val result = WebsiteUrl.tryCreate("not a valid url")
        result.isSuccess shouldBe false
        result.exceptionOrNull() shouldBe URISyntaxException("not a valid url", "Illegal character in path at index 3")
    }

    @Nested
    @DisplayName("toWebsiteUrlOrNull")
    inner class ToWebsiteUrlOrNullTest {
        @Test
        fun `should return a WebsiteUrl when the input is a valid url`() {
            "http://www.google.com".toWebsiteUrlOrNull() shouldBe WebsiteUrl(URI("http://www.google.com"))
        }

        @Test
        fun `should return null when the input is a not valid url`() {
            "not a valid url".toWebsiteUrlOrNull() shouldBe null
        }

        @Test
        fun `should return null when the input is blank`() {
            "".toWebsiteUrlOrNull() shouldBe null
        }

        @Test
        fun `should return null when the input is null`() {
            null.toWebsiteUrlOrNull() shouldBe null
        }
    }
}
