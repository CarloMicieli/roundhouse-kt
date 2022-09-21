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
package com.trenako.infrastructure.persistence.catalog

import com.trenako.address.Address
import com.trenako.catalog.brands.BrandId
import com.trenako.catalog.brands.BrandKind
import com.trenako.catalog.brands.BrandStatus
import com.trenako.catalog.brands.BrandView
import com.trenako.contact.ContactInfo
import com.trenako.contact.MailAddress
import com.trenako.contact.PhoneNumber
import com.trenako.contact.WebsiteUrl
import com.trenako.countries.Country
import com.trenako.metadata.MetadataInfo
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("brands")
data class BrandRow(
    @Id
    val brandId: BrandId,
    val name: String,
    val registeredCompanyName: String?,
    val groupName: String?,
    val description: String?,
    val kind: BrandKind,
    val phoneNumber: PhoneNumber?,
    val websiteUrl: WebsiteUrl?,
    val email: MailAddress?,

    val addressStreetAddress: String?,
    val addressExtendedAddress: String?,
    val addressCity: String?,
    val addressRegion: String?,
    val addressPostalCode: String?,
    val addressCountry: Country?,

    val status: BrandStatus?,
    val version: Int,
    val created: Instant,
    val lastModified: Instant? = null
)

fun BrandRow.toView(): BrandView = BrandView(
    id = this.brandId,
    name = this.name,
    registeredCompanyName = this.registeredCompanyName,
    groupName = this.groupName,
    description = this.description,
    address = this.address(),
    contactInfo = this.contactInfo(),
    kind = this.kind,
    status = this.status,
    metadata = MetadataInfo(
        version = this.version,
        createdAt = this.created,
        lastModified = this.lastModified
    )
)

fun BrandRow.address(): Address? {
    return if (this.addressCountry == null &&
        this.addressStreetAddress.isNullOrBlank() &&
        this.addressCity.isNullOrBlank()
    ) {
        null
    } else {
        Address(
            streetAddress = this.addressStreetAddress!!,
            extendedAddress = this.addressExtendedAddress,
            city = this.addressCity!!,
            postalCode = this.addressPostalCode,
            country = this.addressCountry!!,
            region = this.addressRegion
        )
    }
}

fun BrandRow.contactInfo(): ContactInfo? {
    return if (this.phoneNumber == null &&
        this.websiteUrl == null &&
        this.email == null
    ) {
        null
    } else {
        ContactInfo(
            email = this.email,
            phone = this.phoneNumber,
            websiteUrl = this.websiteUrl
        )
    }
}
