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
package io.github.carlomicieli.roundhouse.catalog.scales

import java.math.BigDecimal

/**
 * It represents the {@code Ratio} between a model railway size
 * and the size of an actual train.
 */
@JvmInline
value class Ratio private constructor(val value: BigDecimal) : Comparable<Ratio> {
    init {
        require(value.signum() == 1) { "ratio value must be positive" }
    }

    override fun compareTo(other: Ratio): Int = other.value.compareTo(this.value)

    override fun toString(): String = "1:$value"

    companion object {
        // Common scala ratios
        private val ratios: Map<BigDecimal, Ratio> = mapOf(
            BigDecimal.valueOf(32) to Ratio(BigDecimal.valueOf(32)),
            BigDecimal.valueOf(43.5) to Ratio(BigDecimal.valueOf(43.5)),
            BigDecimal.valueOf(87) to Ratio(BigDecimal.valueOf(87)),
            BigDecimal.valueOf(120) to Ratio(BigDecimal.valueOf(120)),
            BigDecimal.valueOf(160) to Ratio(BigDecimal.valueOf(160))
        )

        fun of(value: Float): Ratio = of(value.toDouble())

        fun of(value: Double): Ratio = Ratio(BigDecimal.valueOf(value).stripTrailingZeros())

        fun of(value: BigDecimal): Ratio = Ratio(value)

        fun tryCreate(value: Float): Result<Ratio> = tryCreate(value.toDouble())

        fun tryCreate(value: Double): Result<Ratio> = runCatching {
            val v = BigDecimal.valueOf(value).stripTrailingZeros()
            ratios[v] ?: Ratio(v)
        }
    }
}

fun Float?.toRatio(): Ratio? = this?.let { Ratio.tryCreate(it).getOrNull() }

fun Double?.toRatio(): Ratio? = this?.let { Ratio.tryCreate(it).getOrNull() }
