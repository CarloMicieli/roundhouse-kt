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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@DisplayName("Mail address validator")
class MailAddressValidatorTest {
    @ParameterizedTest
    @CsvSource(
        "username@domain.com,true",
        "user.name@domain.com,true",
        "user-name@domain.com,true",
        "username@domain.co.in,true",
        "user_name@domain.com,true",
        "username.@domain.com,false",
        ".user.name@domain.com,false",
        "user-name@domain.com.,false",
        "username@.com,false",
        "foo,false"
    )
    fun `should validate mail address`(email: String, isValid: Boolean) {
        MailAddressValidator.isValid(email) shouldBe isValid
    }
}
