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
package com.trenako.countries

import java.util.Arrays
import java.util.Locale

/**
 * It represents an ISO country.
 *
 * <p>A simple wrapper around country code ({@code ISO 3166 alpha-2} country code), to ensure it
 * allows only valid country codes
 */
data class Country(
    /**
     * The Alpha-2 ISO 3166 code for the country
     */
    val code: String,
    /**
     * The English name for the country (optional)
     */
    val englishName: String? = null
) {

    init {
        require(code.length == 2 && code.isValidCountryCode()) { "'$code' is not a valid Alpha-2 ISO 3166 country code" }
    }

    companion object {

        private val countries: Map<String, Country> = mapOf(
            putCountry("at", "Austria"),
            putCountry("be", "Belgium"),
            putCountry("ca", "Canada"),
            putCountry("cn", "China"),
            putCountry("dk", "Denmark"),
            putCountry("fi", "Finland"),
            putCountry("fr", "France"),
            putCountry("de", "Germany"),
            putCountry("it", "Italy"),
            putCountry("jp", "Japan"),
            putCountry("mx", "Mexico"),
            putCountry("nl", "Netherlands"),
            putCountry("no", "Norway"),
            putCountry("pt", "Portugal"),
            putCountry("ro", "Romania"),
            putCountry("es", "Spain"),
            putCountry("se", "Sweden"),
            putCountry("ch", "Switzerland"),
            putCountry("tr", "Turkey"),
            putCountry("gb", "United Kingdom"),
            putCountry("us", "United States")
        )

        private fun putCountry(code: String, englishName: String): Pair<String, Country> = Pair(code, Country(code, englishName))

        fun of(code: String): Country {
            return countries[code.lowercase()] ?: Country(code.lowercase())
        }
    }
}

/**
 * Returns `true` if the value is a valid 2-letter country code as defined in ISO 3166.
 *
 * @return `true` if the value is a valid country; `false` otherwise
 */
fun String?.isValidCountryCode(): Boolean {
    return if (this.isNullOrBlank()) {
        true
    } else Arrays.binarySearch(Locale.getISOCountries(), this.uppercase(Locale.getDefault())) >= 0
}
