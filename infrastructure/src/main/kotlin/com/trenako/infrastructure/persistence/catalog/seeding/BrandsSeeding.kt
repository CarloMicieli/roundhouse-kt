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

import com.trenako.address.Address
import com.trenako.catalog.brands.BrandId
import com.trenako.catalog.brands.BrandKind
import com.trenako.catalog.brands.BrandStatus
import com.trenako.catalog.brands.createbrands.CreateBrandRepository
import com.trenako.catalog.brands.createbrands.NewBrand
import com.trenako.contact.ContactInfo
import com.trenako.contact.MailAddress
import com.trenako.contact.WebsiteUrl
import com.trenako.countries.Country
import com.trenako.util.Slug
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI

class BrandsSeeding(private val createBrandRepository: CreateBrandRepository) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(BrandsSeeding::class.java)
    }

    suspend fun seed() {
        insert(acme())
        insert(arnold())
        insert(bemo())
        insert(brawa())
        insert(electrotren())
        insert(heris())
        insert(fleischmann())
        insert(jagerndorfer())
        insert(liliput())
        insert(lima())
        insert(lsModels())
        insert(maerklin())
        insert(oskar())
        insert(piko())
        insert(pirata())
        insert(rivarossi())
        insert(roco())
        insert(sudexpress())
        insert(trix())
        insert(vitrains())
    }

    private suspend fun insert(b: NewBrand) {
        createBrandRepository.insert(b)
        log.info("Brand ${b.name} inserted.")
    }
}

fun acme(): NewBrand = NewBrand(
    id = BrandId.of("acme"),
    name = "ACME",
    registeredCompanyName = "Associazione Costruzioni Modellistiche Esatte",
    groupName = null,
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("mail@acmetreni.com"),
        websiteUrl = WebsiteUrl(URI("http://www.acmetreni.com")),
        phone = null
    ),
    address = Address(
        streetAddress = "Viale Lombardia, 27",
        extendedAddress = null,
        postalCode = "20131",
        city = "Milano",
        region = "MI",
        country = Country.of("it")
    ),
    description = ""
)

fun arnold(): NewBrand = NewBrand(
    id = BrandId.of("arnold"),
    name = "Arnold",
    registeredCompanyName = "Arnold model",
    groupName = "hornby",
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("customerservices.it@hornby.com"),
        websiteUrl = WebsiteUrl(URI("https://it.arnoldmodel.com/")),
        phone = null
    ),
    address = null,
    description = ""
)

fun bemo(): NewBrand = NewBrand(
    id = BrandId.of("bemo"),
    name = "BEMO",
    registeredCompanyName = "BEMO Modelleisenbahnen GmbH u. Co KG",
    groupName = null,
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("mail@bemo-modellbahn.de"),
        websiteUrl = WebsiteUrl(URI("https://www.bemo-modellbahn.de")),
        phone = null
    ),
    address = Address(
        streetAddress = "Stuttgarter Strasse 59",
        extendedAddress = null,
        postalCode = "D-73066",
        city = "Uhingen",
        region = null,
        country = Country.of("DE")
    ),
    description = ""
)

fun brawa(): NewBrand = NewBrand(
    id = BrandId.of("brawa"),
    name = "Brawa",
    registeredCompanyName = "BRAWA Artur Braun Modellspielwarenfabrik GmbH & Co. KG",
    groupName = null,
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = null,
        websiteUrl = WebsiteUrl(URI("https://www.brawa.de")),
        phone = null
    ),
    address = Address(
        streetAddress = "Uferstraße 24-30",
        extendedAddress = null,
        postalCode = "D-73630",
        city = "Remshalden",
        region = null,
        country = Country.of("DE")
    ),
    description = ""
)

fun electrotren(): NewBrand = NewBrand(
    id = BrandId.of("electrotren"),
    name = "Electrotren",
    registeredCompanyName = "Electrotren",
    groupName = "hornby",
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("customerservices.it@hornby.com"),
        websiteUrl = WebsiteUrl(URI("https://it.electrotren.com/")),
        phone = null
    ),
    address = null,
    description = ""
)

fun fleischmann(): NewBrand = NewBrand(
    id = BrandId.of("fleischmann"),
    name = "Fleischmann",
    registeredCompanyName = "Modelleisenbahn GmbH",
    groupName = "modelleisenbahn",
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = null,
        websiteUrl = WebsiteUrl(URI("https://www.fleischmann.de")),
        phone = null
    ),
    address = Address(
        streetAddress = "Plainbachstraße 4",
        extendedAddress = null,
        postalCode = "A-5101",
        city = "Bergheim",
        region = null,
        country = Country.of("AT")
    ),
    description = ""
)

fun heris(): NewBrand = NewBrand(
    id = BrandId.of("heris"),
    name = "Heris",
    registeredCompanyName = "Heris-Modelleisenbahn",
    groupName = null,
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("info@heris-modelleisenbahn.de"),
        websiteUrl = WebsiteUrl(URI("https://www.heris-modelleisenbahn.de/")),
        phone = null
    ),
    address = Address(
        streetAddress = "Kaesbachstr. 17",
        extendedAddress = null,
        postalCode = "D-41063",
        city = "Mönchengladbach",
        region = null,
        country = Country.of("DE")
    ),
    description = ""
)

fun jagerndorfer(): NewBrand = NewBrand(
    id = BrandId(Slug("Jägerndorfer Collection")),
    name = "Jägerndorfer Collection",
    registeredCompanyName = "Jägerndorfer Ges.m.b.H.",
    groupName = null,
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = null,
        websiteUrl = WebsiteUrl(URI("https://www.jaegerndorfer.at/")),
        phone = null
    ),
    address = Address(
        streetAddress = "Bundesstraße 20",
        extendedAddress = null,
        postalCode = "A-2563",
        city = "Pottenstein",
        region = null,
        country = Country.of("AT")
    ),
    description = ""
)

fun liliput(): NewBrand = NewBrand(
    id = BrandId.of("liliput"),
    name = "Liliput",
    registeredCompanyName = null,
    groupName = "bachmann",
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("bachmann@liliput.de"),
        websiteUrl = WebsiteUrl(URI("https://liliput.de/")),
        phone = null
    ),
    address = Address(
        streetAddress = "Am Umspannwerk 5",
        extendedAddress = null,
        postalCode = "90518",
        city = "Altdorf b. Nürnberg",
        country = Country.of("AT"),
        region = null
    ),
    description = ""
)

fun lima(): NewBrand = NewBrand(
    id = BrandId.of("lima"),
    name = "Lima",
    registeredCompanyName = "Lima model",
    groupName = "hornby",
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("customerservices.it@hornby.com"),
        websiteUrl = WebsiteUrl(URI("https://it.limamodel.it/")),
        phone = null
    ),
    address = null,
    description = ""
)

fun lsModels(): NewBrand = NewBrand(
    id = BrandId.of("ls-models"),
    name = "L.S. Models",
    registeredCompanyName = "L.S. MODELS EXCLUSIVE SPRL",
    groupName = null,
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("info@lsmodels.com"),
        websiteUrl = WebsiteUrl(URI("http://www.lsmodels.com/")),
        phone = null
    ),
    address = Address(
        streetAddress = "rue bosfagnes 31",
        extendedAddress = null,
        postalCode = "B-4950",
        city = "Sourbrodt",
        region = null,
        country = Country.of("BE")
    ),
    description = ""
)

fun maerklin(): NewBrand = NewBrand(
    id = BrandId(Slug("Märklin")),
    name = "Märklin",
    registeredCompanyName = "Gebr. Märklin & Cie. GmbH",
    groupName = "märklin",
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = null,
        websiteUrl = WebsiteUrl(URI("https://www.maerklin.de")),
        phone = null
    ),
    address = Address(
        streetAddress = "Stuttgarter Straße 55-57",
        extendedAddress = null,
        postalCode = "D-73033",
        city = "Göppingen",
        country = Country.of("DE"),
        region = null
    ),
    description = ""
)

fun oskar(): NewBrand = NewBrand(
    id = BrandId.of("oskar"),
    name = "Os.Kar",
    registeredCompanyName = "Os.kar international",
    groupName = null,
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = null,
        websiteUrl = WebsiteUrl(URI("https://www.oskartrains.eu")),
        phone = null
    ),
    address = null,
    description = ""
)

fun piko(): NewBrand = NewBrand(
    id = BrandId.of("piko"),
    name = "PIKO",
    registeredCompanyName = "PIKO Spielwaren GmbH",
    groupName = null,
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("info@piko.de"),
        websiteUrl = WebsiteUrl(URI("https://www.piko.de")),
        phone = null
    ),
    address = Address(
        streetAddress = "Lutherstraße 30",
        extendedAddress = null,
        postalCode = "D-96515",
        city = "Sonneberg",
        region = null,
        country = Country.of("DE")
    ),
    description = ""
)

fun pirata(): NewBrand = NewBrand(
    id = BrandId.of("pirata"),
    name = "Pi.R.A.T.A.",
    registeredCompanyName = "PIKO Spielwaren GmbH",
    groupName = null,
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("info@piratamodels.it"),
        websiteUrl = WebsiteUrl(URI("hhttps://www.piratamodels.it")),
        phone = null
    ),
    address = Address(
        streetAddress = "Via Montonale Basso 5/G",
        extendedAddress = null,
        postalCode = "25015",
        city = "Desenzano del Garda",
        region = null,
        country = Country.of("IT")
    ),
    description = ""
)

fun rivarossi(): NewBrand = NewBrand(
    id = BrandId.of("rivarossi"),
    name = "Rivarossi",
    registeredCompanyName = "Rivarossi",
    groupName = "hornby",
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("customerservices.it@hornby.com"),
        websiteUrl = WebsiteUrl(URI("https://it.rivarossi.com/")),
        phone = null
    ),
    address = null,
    description = ""
)

fun roco(): NewBrand = NewBrand(
    id = BrandId.of("roco"),
    name = "Roco",
    registeredCompanyName = "Modelleisenbahn GmbH",
    groupName = "modelleisenbahn",
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = MailAddress("webshop@roco.cc"),
        websiteUrl = WebsiteUrl(URI("https://www.roco.cc")),
        phone = null
    ),
    address = Address(
        streetAddress = "Plainbachstraße 4",
        extendedAddress = null,
        postalCode = "A-5101",
        city = "Bergheim",
        region = null,
        country = Country.of("AT")
    ),
    description = ""
)

fun sudexpress(): NewBrand = NewBrand(
    id = BrandId.of("sudexpress-models"),
    name = "Sudexpress models",
    registeredCompanyName = "AVALIARE Engenharia Lda.",
    groupName = null,
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = null,
        websiteUrl = WebsiteUrl(URI("https://www.sudexpressmodels.eu/")),
        phone = null
    ),
    address = Address(
        streetAddress = "Praça Camilo Castelo Branco, 31",
        extendedAddress = "2º Andar - Sala 48",
        postalCode = "4700-209",
        city = "Braga",
        region = null,
        country = Country.of("PT")
    ),
    description = ""
)

fun trix(): NewBrand = NewBrand(
    id = BrandId(Slug("trix")),
    name = "Trix",
    registeredCompanyName = "Gebr. Märklin & Cie. GmbH",
    groupName = "märklin",
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = null,
        websiteUrl = WebsiteUrl(URI("https://www.trix.de")),
        phone = null
    ),
    address = Address(
        streetAddress = "Stuttgarter Straße 55-57",
        extendedAddress = null,
        postalCode = "D-73033",
        city = "Göppingen",
        country = Country.of("DE"),
        region = null
    ),
    description = ""
)

fun vitrains(): NewBrand = NewBrand(
    id = BrandId.of("vitrains"),
    name = "ViTrains",
    registeredCompanyName = "Modelleisenbahn GmbH",
    groupName = "modelleisenbahn",
    kind = BrandKind.INDUSTRIAL,
    status = BrandStatus.ACTIVE,
    contactInfo = ContactInfo(
        email = null,
        websiteUrl = WebsiteUrl(URI("https://www.vitrains.it/")),
        phone = null
    ),
    address = Address(
        streetAddress = "Via Cà Zusto, 99",
        extendedAddress = null,
        postalCode = "35010",
        city = "Vigodarzere",
        region = "PD",
        country = Country.of("IT")
    ),
    description = ""
)
