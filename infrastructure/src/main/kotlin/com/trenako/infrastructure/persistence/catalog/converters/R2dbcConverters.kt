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
package com.trenako.infrastructure.persistence.catalog.converters

import com.trenako.catalog.brands.BrandKind
import com.trenako.catalog.brands.BrandStatus
import com.trenako.contact.MailAddress
import com.trenako.contact.PhoneNumber
import com.trenako.contact.WebsiteUrl
import com.trenako.countries.Country
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.convert.EnumWriteSupport

@WritingConverter
class BrandStatusWritingConverter : EnumWriteSupport<BrandStatus>()

@WritingConverter
class BrandKindWritingConverter : EnumWriteSupport<BrandKind>()

@WritingConverter
class PhoneNumberWritingConverter : Converter<PhoneNumber, String> {
    override fun convert(source: PhoneNumber): String = source.value
}

@WritingConverter
class WebsiteUrlWritingConverter : Converter<WebsiteUrl, String> {
    override fun convert(source: WebsiteUrl): String = source.value.toString()
}

@WritingConverter
class MailAddressWritingConverter : Converter<MailAddress, String> {
    override fun convert(source: MailAddress): String = source.value
}

@WritingConverter
class CountryWritingConverter : Converter<Country, String> {
    override fun convert(source: Country): String = source.code
}
