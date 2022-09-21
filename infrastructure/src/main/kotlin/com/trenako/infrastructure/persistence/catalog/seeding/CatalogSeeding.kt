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
package com.trenako.infrastructure.persistence.catalog.seeding

import com.trenako.infrastructure.persistence.catalog.BrandsRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CatalogSeeding(private val brands: BrandsRepository) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(CatalogSeeding::class.java)
    }

    suspend fun seed() {
        if (brands.exists("ACME")) {
            log.info("Database already contains data - seeding skipped")
            return
        }

        log.info("Running catalog database seeding...")
        val brandsSeeding = BrandsSeeding(brands)
        brandsSeeding.seed()
    }
}
