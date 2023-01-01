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
package io.github.carlomicieli.roundhouse.infrastructure.persistence.catalog

import io.github.carlomicieli.roundhouse.address.Address
import io.github.carlomicieli.roundhouse.catalog.brands.BrandId
import io.github.carlomicieli.roundhouse.catalog.brands.BrandKind
import io.github.carlomicieli.roundhouse.catalog.brands.BrandStatus
import io.github.carlomicieli.roundhouse.catalog.brands.BrandView
import io.github.carlomicieli.roundhouse.contact.ContactInfo
import io.github.carlomicieli.roundhouse.contact.MailAddress
import io.github.carlomicieli.roundhouse.contact.PhoneNumber
import io.github.carlomicieli.roundhouse.contact.WebsiteUrl
import io.github.carlomicieli.roundhouse.countries.Country
import io.github.carlomicieli.roundhouse.metadata.MetadataInfo
import io.github.carlomicieli.roundhouse.organizations.OrganizationEntityType
import io.github.carlomicieli.roundhouse.socials.Handler
import io.github.carlomicieli.roundhouse.socials.Socials
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("brands")
data class BrandRow(
    @Id
    val brandId: BrandId,
    val name: String,
    val registeredCompanyName: String?,
    val organizationEntityType: OrganizationEntityType?,
    val groupName: String?,
    val description: String?,
    val kind: BrandKind,
    val contactPhoneNumber: PhoneNumber?,
    val contactWebsiteUrl: WebsiteUrl?,
    val contactEmail: MailAddress?,

    val addressStreetAddress: String?,
    val addressExtendedAddress: String?,
    val addressCity: String?,
    val addressRegion: String?,
    val addressPostalCode: String?,
    val addressCountry: Country?,

    val socialsFacebook: String?,
    val socialsInstagram: String?,
    val socialsLinkedin: String?,
    val socialsTwitter: String?,
    val socialsYoutube: String?,

    val status: BrandStatus?,
    val version: Int,
    val created: Instant,
    val lastModified: Instant? = null
)

fun BrandRow.toView(): BrandView = BrandView(
    id = this.brandId,
    name = this.name,
    registeredCompanyName = this.registeredCompanyName,
    organizationEntityType = this.organizationEntityType,
    groupName = this.groupName,
    description = this.description,
    address = this.address(),
    contactInfo = this.contactInfo(),
    socials = this.socials(),
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
    return if (this.contactPhoneNumber == null &&
        this.contactWebsiteUrl == null &&
        this.contactEmail == null
    ) {
        null
    } else {
        ContactInfo(
            email = this.contactEmail,
            phone = this.contactPhoneNumber,
            websiteUrl = this.contactWebsiteUrl
        )
    }
}

fun BrandRow.socials(): Socials? {
    return if (this.socialsFacebook == null &&
        this.socialsInstagram == null &&
        this.socialsLinkedin == null &&
        this.socialsTwitter == null &&
        this.socialsYoutube == null
    ) {
        null
    } else {
        Socials(
            facebook = this.socialsFacebook.toSocialHandler(),
            instagram = this.socialsInstagram.toSocialHandler(),
            linkedin = this.socialsLinkedin.toSocialHandler(),
            twitter = this.socialsTwitter.toSocialHandler(),
            youtube = this.socialsYoutube.toSocialHandler()
        )
    }
}

fun String?.toSocialHandler(): Handler {
    return Handler(this!!)
}
