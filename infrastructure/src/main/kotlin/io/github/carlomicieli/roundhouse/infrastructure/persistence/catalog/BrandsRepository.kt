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

import io.github.carlomicieli.roundhouse.catalog.brands.BrandId
import io.github.carlomicieli.roundhouse.catalog.brands.BrandView
import io.github.carlomicieli.roundhouse.catalog.brands.createbrands.CreateBrandRepository
import io.github.carlomicieli.roundhouse.catalog.brands.createbrands.NewBrand
import io.github.carlomicieli.roundhouse.catalog.brands.getbrandbyid.GetBrandByIdRepository
import io.github.carlomicieli.roundhouse.catalog.brands.getbrands.GetBrandsRepository
import io.github.carlomicieli.roundhouse.infrastructure.persistence.queries.select
import io.github.carlomicieli.roundhouse.infrastructure.persistence.queries.selectOne
import io.github.carlomicieli.roundhouse.queries.pagination.Page
import io.github.carlomicieli.roundhouse.queries.sorting.Sorting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria.where
import java.time.Clock

class BrandsRepository(private val r2dbcEntityTemplate: R2dbcEntityTemplate, private val clock: Clock) :
    CreateBrandRepository,
    GetBrandByIdRepository,
    GetBrandsRepository {

    override suspend fun exists(name: String): Boolean {
        val query = selectOne {
            where("name").`is`(name)
        }
        return r2dbcEntityTemplate
            .exists(query, ENTITY)
            .awaitSingle()
    }

    override suspend fun insert(newBrand: NewBrand) {
        val newRow = BrandRow(
            brandId = newBrand.id,
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
            addressCountry = newBrand.address?.country,
            status = newBrand.status,
            version = 0,
            created = clock.instant(),
            lastModified = null
        )
        r2dbcEntityTemplate.insert(newRow).awaitSingle()
    }

    override suspend fun findById(brandId: io.github.carlomicieli.roundhouse.catalog.brands.BrandId): BrandView? {
        val query = selectOne {
            where("brand_id").`is`(brandId.toString())
        }
        return r2dbcEntityTemplate
            .selectOne(query, ENTITY)
            .map { it.toView() }
            .awaitSingleOrNull()
    }

    override fun findAll(currentPage: Page, orderBy: Sorting): Flow<BrandView> {
        val query = select(currentPage, orderBy)
        return r2dbcEntityTemplate
            .select(query, ENTITY)
            .map { it.toView() }
            .asFlow()
    }

    companion object {
        private val ENTITY: Class<BrandRow> = BrandRow::class.java
    }
}
