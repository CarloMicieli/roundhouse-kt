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
package io.github.carlomicieli.roundhouse.catalog.railways

import io.github.carlomicieli.roundhouse.util.Slug

/**
 * It represents a Railway unique identifier. The value should be url encoded.
 */
data class RailwayId(private val value: Slug) : Comparable<RailwayId> {
    override fun compareTo(other: RailwayId): Int = this.toString().compareTo(other.toString())

    override fun toString(): String = value.toString()

    companion object {
        /**
         * Creates a new {@code RailwayId} from the input string.
         *
         * Throws an {@code IllegalArgumentException} when the input string is blank.
         *
         * @return a new {@code RailwayId}
         */
        fun of(value: String): RailwayId {
            require(value.isNotBlank()) { "Railway id cannot be blank" }
            return RailwayId(Slug(value))
        }
    }
}
