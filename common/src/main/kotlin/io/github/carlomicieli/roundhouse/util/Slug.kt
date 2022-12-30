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

import java.text.Normalizer
import java.util.regex.Pattern

class Slug(value: String) {
    private val value: String

    init {
        require(value.isNotBlank()) { "Slug value cannot be blank" }
        this.value = value.toSeoFriendlyString()
    }

    override fun toString(): String = value

    override fun hashCode(): Int = value.hashCode()

    override fun equals(other: Any?) = (other is Slug) && value == other.value
}

private val NON_LATIN: Pattern = Pattern.compile("[^\\w-]")
private val WHITESPACE: Pattern = Pattern.compile("[\\s]")
private const val SEP = "-"

/**
 * Convert the String to a SEO friendly one
 */
fun String.toSeoFriendlyString(): String {
    val noWhitespace = WHITESPACE.matcher(this).replaceAll(SEP)
    val normalized: String = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD)
    return NON_LATIN.matcher(normalized).replaceAll("").lowercase()
}
