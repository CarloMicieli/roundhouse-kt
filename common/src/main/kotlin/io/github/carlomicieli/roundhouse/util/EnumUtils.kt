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

object EnumUtils {
    /**
     * It checks if the {@code String} is a valid name for the enumeration
     * @param T the enumeration
     */
    inline fun <reified T : Enum<T>> String.isValidName() =
        enumValues<T>().map {
            it.name.uppercase()
        }.contains(this.uppercase())

    /**
     * It converts the {@code String} to the corresponding enumeration name, throwing an
     * {@code IllegalArgumentException} if the input is not a valid name
     *
     * @param T the enumeration
     */
    inline fun <reified T : Enum<T>> String.toEnum(): T = enumValueOf(this.uppercase())

    /**
     * It converts the {@code String} to the corresponding enumeration name, returning
     * {@code null} if the input is not a valid enumeration name
     *
     * @param T the enumeration
     */
    inline fun <reified T : Enum<T>> String?.toEnumOrNull(): T? =
        if (this == null) {
            null
        } else {
            if (this.isValidName<T>()) {
                this.toEnum<T>()
            } else {
                null
            }
        }
}
