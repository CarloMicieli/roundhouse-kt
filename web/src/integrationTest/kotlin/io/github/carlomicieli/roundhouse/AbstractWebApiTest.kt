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
package io.github.carlomicieli.roundhouse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@Testcontainers
abstract class AbstractWebApiTest {

    companion object {
        private val postgresContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15.1-alpine")
            .withReuse(true)

        init {
            postgresContainer.start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            with(registry) {
                add("spring.liquibase.enabled") { "true" }
                add("spring.liquibase.url", postgresContainer::getJdbcUrl)
                add("spring.liquibase.user", postgresContainer::getUsername)
                add("spring.liquibase.password", postgresContainer::getPassword)
                add("spring.liquibase.change-log") { "classpath:db/changelog/db.changelog-master.xml" }

                val url = postgresContainer.jdbcUrl.replace("jdbc:", "r2dbc:")
                add("spring.r2dbc.url") { url }
                add("spring.r2dbc.username", postgresContainer::getUsername)
                add("spring.r2dbc.password", postgresContainer::getPassword)
            }
        }
    }

    @Autowired
    lateinit var webClient: WebTestClient
}
