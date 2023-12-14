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
package io.github.carlomicieli.roundhouse.catalog.brands.createbrands

import io.github.carlomicieli.roundhouse.contact.ContactInfo
import io.github.carlomicieli.roundhouse.contact.MailAddress
import io.github.carlomicieli.roundhouse.contact.PhoneNumber
import io.github.carlomicieli.roundhouse.contact.WebsiteUrl
import io.github.carlomicieli.roundhouse.validation.annotation.constraints.ValidWebsiteUrl
import jakarta.validation.constraints.Size
import java.net.URI

data class CreateBrandContactInfo(
    @field:Size(min = 3, max = 100)
    val email: String?,
    @field:Size(min = 3, max = 100)
    @field:ValidWebsiteUrl
    val websiteUrl: String?,
    @field:Size(min = 3, max = 20)
    val phoneNumber: String?
) {
    fun toContactInfo(): ContactInfo? {
        val mailAddress = if (this.email == null) null else MailAddress(this.email)
        val phone = if (this.phoneNumber == null) null else PhoneNumber(this.phoneNumber)
        val websiteUrl = if (this.websiteUrl == null) null else WebsiteUrl(URI(this.websiteUrl))
        return ContactInfo(mailAddress, websiteUrl, phone)
    }
}
