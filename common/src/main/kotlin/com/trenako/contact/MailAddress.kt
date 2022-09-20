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

/**
 * A mail address
 */
data class MailAddress(val value: String) {
    init {
        require(value.isNotBlank()) { "A mail address cannot be blank" }
        require(MailAddressValidator.isValid(value)) { "$value is not a valid mail address" }
    }

    companion object {
        fun tryCreate(email: String): MailAddress? {
            return if (email.isNotBlank() && MailAddressValidator.isValid(email)) {
                MailAddress(email)
            } else {
                null
            }
        }
    }
}

/**
 * Try to convert this {@code String} into a {@code MailAddress}, in case the {@code String} is
 * not a valid mail this method will throw an exception
 *
 * @return a {@code MailAddress}
 */
fun String.toMailAddress(): MailAddress = MailAddress(this)

/**
 * Try to convert this {@code String} into a {@code MailAddress}, in case the {@code String} is
 * not a valid mail this method will return {@code null}
 *
 * @return a {@code MailAddress}, or {@code null} if the {@code String} is not a valid mail address
 */
fun String?.toMailAddressOrNull(): MailAddress? = this?.let { MailAddress.tryCreate(this) }
