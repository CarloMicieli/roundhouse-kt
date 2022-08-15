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
package com.trenako.catalog.brands.createbrands

import com.trenako.catalog.brands.BrandKind
import com.trenako.contact.ContactInfo

/**
 * A repository for the {@code CreateBrandUseCase} to handle all persistence requirements
 */
interface CreateBrandRepository {
    /**
     * Check if a brand with the same name already exists.
     * @param name the brand name
     */
    suspend fun exists(name: String): Boolean

    /**
     * Persist a new brand.
     *
     * This method is executed only for its side effect, hence if the operation is successful the method is not
     * producing any meaningful output.
     *
     * @param newBrand the new brand to insert
     * @return either an {@code Error} or Unit
     */
    suspend fun insert(newBrand: NewBrand)

    /**
     * A DTO for a new brand
     */
    data class NewBrand(
        val id: BrandId,
        val name: String,
        val kind: BrandKind,
        val contactInfo: ContactInfo?,
        val active: Boolean?
    )
}
