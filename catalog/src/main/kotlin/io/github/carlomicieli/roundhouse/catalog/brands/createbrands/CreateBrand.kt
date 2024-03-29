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

import io.github.carlomicieli.roundhouse.catalog.brands.validation.constraints.ValidBrandKind
import io.github.carlomicieli.roundhouse.catalog.brands.validation.constraints.ValidBrandStatus
import io.github.carlomicieli.roundhouse.validation.annotation.constraints.ValidOrganizationEntityType
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateBrand(
    @field:NotBlank
    @field:Size(min = 3, max = 100)
    val name: String = "",
    @field:NotBlank
    @field:ValidBrandKind
    val kind: String = "",
    @field:Size(min = 3, max = 100)
    val registeredCompanyName: String? = null,
    @field:ValidOrganizationEntityType
    val organizationEntityType: String? = null,
    @field:Size(min = 3, max = 50)
    val groupName: String? = null,
    @field:Size(min = 3, max = 1000)
    val description: String? = null,
    @field:Valid
    val contactInfo: CreateBrandContactInfo? = null,
    @field:Valid
    val address: CreateBrandAddress? = null,
    val socials: CreateBrandSocials? = null,
    @field:ValidBrandStatus
    val status: String? = null
)
