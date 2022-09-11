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

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Mail address")
class MailAddressTest {
    @Test
    fun `cannot be blank`() {
        val exception = shouldThrowExactly<IllegalArgumentException> {
            MailAddress("")
        }
        exception.message shouldBe "A mail address cannot be blank"
    }

    @Test
    fun `is created from non-empty Strings`() {
        val email = "mail@mail.com".toMailAddress()
        email shouldBe MailAddress("mail@mail.com")
    }

    @Test
    fun `is created from nullable Strings`() {
        "mail@mail.com".toMailAddressOrNull() shouldBe MailAddress("mail@mail.com")
        "".toMailAddressOrNull() shouldBe null
        null.toMailAddressOrNull() shouldBe null
    }
}
