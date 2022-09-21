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

import com.trenako.address.Address
import com.trenako.catalog.brands.BrandId
import com.trenako.catalog.brands.BrandKind
import com.trenako.catalog.brands.BrandStatus
import com.trenako.contact.ContactInfo

/**
 * A new brand
 */
data class NewBrand(
    val id: BrandId,
    val name: String,
    val registeredCompanyName: String?,
    val groupName: String?,
    val description: String?,
    val kind: BrandKind,
    val contactInfo: ContactInfo?,
    val address: Address?,
    val status: BrandStatus?
)
