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
package com.trenako.infrastructure.persistence.catalog

import com.trenako.address.Address
import com.trenako.catalog.brands.BrandKind
import com.trenako.catalog.brands.BrandStatus
import com.trenako.catalog.brands.createbrands.BrandId
import com.trenako.catalog.brands.createbrands.CreateBrandRepository
import com.trenako.contact.ContactInfo
import com.trenako.contact.MailAddress
import com.trenako.contact.WebsiteUrl
import com.trenako.countries.Country
import com.trenako.util.Slug
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI

class CatalogSeeding(private val brands: R2dbcBrandsRepository) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(CatalogSeeding::class.java)
    }

    suspend fun seed() {
        if (brands.exists("ACME")) {
            log.info("Database already contains data - seeding skipped")
            return
        }

        log.info("Running catalog database seeding...")
        insertBrand(Brands.acme())
        insertBrand(Brands.arnold())
        insertBrand(Brands.bemo())
        insertBrand(Brands.brawa())
        insertBrand(Brands.electrotren())
        insertBrand(Brands.heris())
        insertBrand(Brands.fleischmann())
        insertBrand(Brands.jagerndorfer())
        insertBrand(Brands.liliput())
        insertBrand(Brands.lima())
        insertBrand(Brands.lsModels())
        insertBrand(Brands.maerklin())
        insertBrand(Brands.oskar())
        insertBrand(Brands.piko())
        insertBrand(Brands.pirata())
        insertBrand(Brands.rivarossi())
        insertBrand(Brands.roco())
        insertBrand(Brands.sudexpress())
        insertBrand(Brands.trix())
        insertBrand(Brands.vitrains())
    }

    private suspend fun insertBrand(b: CreateBrandRepository.NewBrand) {
        brands.insert(b)
        log.info("Brand ${b.name} inserted.")
    }
}

object Brands {
    fun acme(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("it")
        ),
        description = ""
    )

    fun arnold(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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

    fun bemo(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("DE")
        ),
        description = ""
    )

    fun brawa(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("DE")
        ),
        description = ""
    )

    fun electrotren(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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

    fun fleischmann(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("AT")
        ),
        description = ""
    )

    fun heris(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("DE")
        ),
        description = ""
    )

    fun jagerndorfer(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("AT")
        ),
        description = ""
    )

    fun liliput(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("AT"),
            region = null
        ),
        description = ""
    )

    fun lima(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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

    fun lsModels(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("BE")
        ),
        description = ""
    )

    fun maerklin(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("DE"),
            region = null
        ),
        description = ""
    )

    fun oskar(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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

    fun piko(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("DE")
        ),
        description = ""
    )

    fun pirata(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("IT")
        ),
        description = ""
    )

    fun rivarossi(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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

    fun roco(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("AT")
        ),
        description = ""
    )

    fun sudexpress(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("PT")
        ),
        description = ""
    )

    fun trix(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("DE"),
            region = null
        ),
        description = ""
    )

    fun vitrains(): CreateBrandRepository.NewBrand = CreateBrandRepository.NewBrand(
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
            countryCode = Country.of("IT")
        ),
        description = ""
    )
}
