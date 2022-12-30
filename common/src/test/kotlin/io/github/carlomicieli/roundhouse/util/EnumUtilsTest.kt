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
package io.github.carlomicieli.roundhouse.util

import io.github.carlomicieli.roundhouse.util.EnumUtils.isValidName
import io.github.carlomicieli.roundhouse.util.EnumUtils.toEnum
import io.github.carlomicieli.roundhouse.util.EnumUtils.toEnumOrNull
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Enum utils")
class EnumUtilsTest {
    @Test
    fun `should check if a string is a valid enum name`() {
        val input = MyEnum.OPTION_1.name
        input.isValidName<MyEnum>() shouldBe true
    }

    @Test
    fun `should check if a string is a valid enum name, ignoring the case`() {
        val input = MyEnum.OPTION_1.name.lowercase()
        input.isValidName<MyEnum>() shouldBe true
    }

    @Test
    fun `should check if a string is a not a valid enum name`() {
        val input = "NOT_A_VALID_OPTION"
        input.isValidName<MyEnum>() shouldBe false
    }

    @Test
    fun `should convert nullable Strings to enum`() {
        val kind = MyEnum.OPTION_1.name.toEnumOrNull<MyEnum>()
        kind shouldBe MyEnum.OPTION_1
    }

    @Test
    fun `should return null trying to convert null to enum`() {
        val input: String? = null
        val kind = input.toEnumOrNull<MyEnum>()
        kind shouldBe null
    }

    @Test
    fun `should convert a String to enum, ignoring the case`() {
        val input = "option_1"
        val kind = input.toEnumOrNull<MyEnum>()
        kind shouldBe MyEnum.OPTION_1
    }

    @Test
    fun `should return null when the string to convert is not representing a valid enum name`() {
        val kind = "NOT_A_VALID_OPTION".toEnumOrNull<MyEnum>()
        kind shouldBe null
    }

    @Test
    fun `should convert a String to enum name`() {
        val kind = MyEnum.OPTION_1.name.toEnum<MyEnum>()
        kind shouldBe MyEnum.OPTION_1
    }

    @Test
    fun `should throw an IllegalArgumentException for invalid values`() {
        val exception = shouldThrowExactly<IllegalArgumentException> { "NOT_A_VALID_OPTION".toEnum<MyEnum>() }
        exception.message shouldStartWith "No enum constant"
    }

    enum class MyEnum {
        OPTION_1
    }
}
