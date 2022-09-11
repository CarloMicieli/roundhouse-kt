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
@file:Suppress("SpringDataRepositoryMethodReturnTypeInspection")

package com.trenako.infrastructure.persistence.catalog

import com.trenako.catalog.brands.BrandKind
import com.trenako.catalog.brands.BrandStatus
import com.trenako.catalog.brands.createbrands.CreateBrandRepository
import com.trenako.contact.MailAddress
import com.trenako.contact.PhoneNumber
import com.trenako.contact.WebsiteUrl
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.Instant

class R2dbcBrandsRepository(private val repository: CoroutineBrandsCrudRepository) : CreateBrandRepository {
    override suspend fun exists(name: String): Boolean = repository.existsByName(name)

    override suspend fun insert(newBrand: CreateBrandRepository.NewBrand) {
        val dto = BrandDto(
            brandId = newBrand.id.toString(),
            name = newBrand.name,
            registeredCompanyName = newBrand.registeredCompanyName,
            groupName = newBrand.groupName,
            description = newBrand.description,
            phoneNumber = newBrand.contactInfo?.phone,
            email = newBrand.contactInfo?.email,
            websiteUrl = newBrand.contactInfo?.websiteUrl,
            kind = newBrand.kind,
            addressStreetAddress = newBrand.address?.streetAddress,
            addressExtendedAddress = newBrand.address?.extendedAddress,
            addressCity = newBrand.address?.city,
            addressRegion = newBrand.address?.region,
            addressPostalCode = newBrand.address?.postalCode,
            addressCountryCode = newBrand.address?.country?.code,
            status = newBrand.status,
            version = 0,
            created = Instant.now()
        )
        repository.save(dto)
    }
}

interface CoroutineBrandsCrudRepository : CoroutineCrudRepository<BrandDto, String> {
    suspend fun existsByName(name: String): Boolean
}

@Table("brands")
data class BrandDto(
    @Id
    val brandId: String,
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
    val addressCountryCode: String?,

    val status: BrandStatus?,
    @Version
    var version: Int = 0,
    val created: Instant,
    val lastModified: Instant? = null
)
