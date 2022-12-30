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

data class TwoLengths(val measureUnit1: MeasureUnit, val measureUnit2: MeasureUnit) {

    init {
        require(measureUnit1 != measureUnit2) { "the two measure units must be different" }
    }

    operator fun invoke(lhs: Double?, rhs: Double?): Pair<Length, Length>? {
        return if (lhs != null && rhs != null) {
            val len1 = Length.valueOf(lhs, measureUnit1)
            val len2 = Length.valueOf(rhs, measureUnit2)
            Pair(len1, len2)
        } else if (lhs != null) {
            val len1 = Length.valueOf(lhs, measureUnit1)
            val len2 = len1.convertTo(measureUnit2)
            Pair(len1, len2)
        } else if (rhs != null) {
            val len2 = Length.valueOf(rhs, measureUnit2)
            val len1 = len2.convertTo(measureUnit1)
            Pair(len1, len2)
        } else {
            null
        }
    }
}
