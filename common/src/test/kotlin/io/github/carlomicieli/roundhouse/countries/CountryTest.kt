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
package io.github.carlomicieli.roundhouse.countries

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("country")
class CountryTest {
    @ParameterizedTest
    @ValueSource(strings = ["", "a", "aaa", "something wrong"])
    fun `should check the country code length`(code: String) {
        val exception = shouldThrowExactly<IllegalArgumentException> {
            Country.of(code)
        }

        exception.message shouldBe "'$code' is not a valid Alpha-2 ISO 3166 country code"
    }

    @Test
    fun `should fill the English name for common country codes`() {
        val country1 = Country.of("de")
        val country2 = Country.of("DE")
        country1.apply {
            code shouldBe "de"
            englishName shouldBe "Germany"
        }
        country2.apply {
            code shouldBe "de"
            englishName shouldBe "Germany"
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["af", "am", "de", "us"])
    fun `should check the country code is a valid Alpha-2 ISO 3166 code`(code: String) {
        val country = Country.of(code)
        country.code shouldBe code
    }

    @ParameterizedTest
    @ValueSource(strings = ["dd", "yy"])
    fun `should check whether the country code is not a valid Alpha-2 ISO 3166 code`(code: String) {
        val exception = shouldThrowExactly<IllegalArgumentException> {
            Country.of(code)
        }

        exception.message shouldBe "'$code' is not a valid Alpha-2 ISO 3166 country code"
    }
}
