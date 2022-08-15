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
package com.trenako.address

/**
 * An immutable object value that represents an {@code Address}.
 */
data class Address(
    val streetAddress: String,
    val extendedAddress: String?,
    val city: String,
    val region: String?,
    val postalCode: String?,
    val countryCode: String
)

/**
 * Checks whether the provided {@code address} is empty.
 *
 * <p>An {@code address} is empty if it doesn't contain a valid value for at least one of the
 * following fields: {@code streetAddress}, {@code city} or {@code country}.
 *
 * @return {@code true} if the {@code Address} is empty, {@code false} otherwise.
 */
fun Address?.isEmpty(): Boolean {
    return if (this == null) {
        true
    } else {
        city.isBlank() || streetAddress.isBlank() || countryCode.isBlank()
    }
}
