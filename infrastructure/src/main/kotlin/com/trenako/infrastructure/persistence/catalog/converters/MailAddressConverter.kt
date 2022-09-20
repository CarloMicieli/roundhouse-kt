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

import com.trenako.contact.MailAddress
import com.trenako.contact.toMailAddressOrNull
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class MailAddressWriteConverter : Converter<MailAddress, String> {
    override fun convert(source: MailAddress): String = source.value
}

@ReadingConverter
object MailAddressReadConverter : Converter<String, MailAddress> {
    override fun convert(source: String): MailAddress? = runCatching { source.toMailAddressOrNull() }.getOrNull()
}
