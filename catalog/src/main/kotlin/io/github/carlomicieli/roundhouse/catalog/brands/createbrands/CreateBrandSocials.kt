/*
 *   Copyright (c) 2021-2023 (C) Carlo Micieli
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
package io.github.carlomicieli.roundhouse.catalog.brands.createbrands

import io.github.carlomicieli.roundhouse.socials.Handler
import io.github.carlomicieli.roundhouse.socials.Socials

data class CreateBrandSocials(
    val facebook: String?,
    val instagram: String?,
    val linkedin: String?,
    val twitter: String?,
    val youtube: String?
) {
    fun toSocials(): Socials =
        Socials(
            facebook = this.facebook.toHandler(),
            instagram = this.instagram.toHandler(),
            linkedin = this.linkedin.toHandler(),
            twitter = this.twitter.toHandler(),
            youtube = this.youtube.toHandler()
        )
}

fun String?.toHandler(): Handler? {
    return if (this.isNullOrBlank()) {
        null
    } else {
        Handler(this)
    }
}
