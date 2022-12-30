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

import io.github.carlomicieli.roundhouse.address.Address
import io.github.carlomicieli.roundhouse.countries.Country
import io.github.carlomicieli.roundhouse.validation.annotation.constraints.ISOCountryCode
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class CreateBrandAddress(
    @field:NotBlank
    @field:Size(max = 255)
    val streetAddress: String = "",

    @field:Size(max = 255)
    val extendedAddress: String?,

    @field:NotBlank
    @field:Size(max = 50)
    val city: String = "",

    @field:Size(max = 50)
    val region: String?,

    @field:Size(max = 10)
    val postalCode: String?,

    @field:NotBlank
    @field:ISOCountryCode
    val countryCode: String = ""
) {

    fun toAddress(): Address = Address(
        streetAddress = this.streetAddress,
        extendedAddress = this.extendedAddress,
        city = this.city,
        region = this.region,
        postalCode = this.postalCode,
        country = Country.of(this.countryCode)
    )
}
