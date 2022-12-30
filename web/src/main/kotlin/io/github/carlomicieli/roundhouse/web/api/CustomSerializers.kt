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
package io.github.carlomicieli.roundhouse.web.api

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import io.github.carlomicieli.roundhouse.catalog.brands.BrandId
import io.github.carlomicieli.roundhouse.catalog.scales.ScaleId
import io.github.carlomicieli.roundhouse.contact.MailAddress
import io.github.carlomicieli.roundhouse.contact.PhoneNumber
import io.github.carlomicieli.roundhouse.contact.WebsiteUrl
import io.github.carlomicieli.roundhouse.util.Slug
import io.github.carlomicieli.roundhouse.util.URN

object URNSerializer : StdSerializer<URN>(URN::class.java) {
    override fun serialize(value: URN?, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value?.value)
    }
}

object SlugSerializer : StdSerializer<Slug>(Slug::class.java) {
    override fun serialize(value: Slug?, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value?.toString())
    }
}

object BrandIdSerializer : StdSerializer<io.github.carlomicieli.roundhouse.catalog.brands.BrandId>(io.github.carlomicieli.roundhouse.catalog.brands.BrandId::class.java) {
    override fun serialize(value: io.github.carlomicieli.roundhouse.catalog.brands.BrandId?, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value?.toString())
    }
}

object MailAddressSerializer : StdSerializer<MailAddress>(MailAddress::class.java) {
    override fun serialize(value: MailAddress?, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value?.value)
    }
}

object PhoneNumberSerializer : StdSerializer<PhoneNumber>(PhoneNumber::class.java) {
    override fun serialize(value: PhoneNumber?, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value?.toString())
    }
}

object ScaleIdSerializer : StdSerializer<ScaleId>(ScaleId::class.java) {
    override fun serialize(value: ScaleId?, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value?.toString())
    }
}

object WebsiteUrlSerializer : StdSerializer<WebsiteUrl>(WebsiteUrl::class.java) {
    override fun serialize(value: WebsiteUrl?, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeString(value?.toString())
    }
}

fun customSerializerModule(): SimpleModule {
    val module = SimpleModule()
    with(module) {
        addSerializer(BrandIdSerializer)
        addSerializer(MailAddressSerializer)
        addSerializer(PhoneNumberSerializer)
        addSerializer(ScaleIdSerializer)
        addSerializer(SlugSerializer)
        addSerializer(URNSerializer)
        addSerializer(WebsiteUrlSerializer)
    }
    return module
}
