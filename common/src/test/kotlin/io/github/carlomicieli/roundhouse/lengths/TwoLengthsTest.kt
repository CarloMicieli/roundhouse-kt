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
package io.github.carlomicieli.roundhouse.lengths

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Two lengths")
class TwoLengthsTest {
    @Test
    fun `should throw an illegal argument exception when the two measure units are the same`() {
        val ex = shouldThrowExactly<IllegalArgumentException> {
            TwoLengths(MeasureUnit.MILLIMETERS, MeasureUnit.MILLIMETERS)
        }

        ex.message shouldBe "the two measure units must be different"
    }

    @Test
    fun `should create two Length values with the inputs`() {
        val twoLengths = TwoLengths(MeasureUnit.MILLIMETERS, MeasureUnit.INCHES)
        val (len1, len2) = twoLengths(16.5, 0.65)!!

        len1 shouldBe Length.valueOf(16.5, MeasureUnit.MILLIMETERS)
        len2 shouldBe Length.valueOf(0.65, MeasureUnit.INCHES)
    }

    @Test
    fun `should create two Length values with the input, converting the input using the second measure unit`() {
        val twoLengths = TwoLengths(MeasureUnit.MILLIMETERS, MeasureUnit.INCHES)
        val (len1, len2) = twoLengths(16.5, null)!!

        len1 shouldBe Length.valueOf(16.5, MeasureUnit.MILLIMETERS)
        len2 shouldBe Length.valueOf(0.65, MeasureUnit.INCHES)
    }

    @Test
    fun `should create two Length values with the input, converting the input using the first measure unit`() {
        val twoLengths = TwoLengths(MeasureUnit.MILLIMETERS, MeasureUnit.INCHES)
        val (len1, len2) = twoLengths(null, 0.65)!!

        len1 shouldBe Length.valueOf(16.51, MeasureUnit.MILLIMETERS)
        len2 shouldBe Length.valueOf(0.65, MeasureUnit.INCHES)
    }

    @Test
    fun `should return null when both inputs are nulls`() {
        val twoLengths = TwoLengths(MeasureUnit.MILLIMETERS, MeasureUnit.INCHES)
        val result = twoLengths(null, null)

        result shouldBe null
    }
}
