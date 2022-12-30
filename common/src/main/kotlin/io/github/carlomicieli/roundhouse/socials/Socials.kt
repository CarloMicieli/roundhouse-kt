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
package io.github.carlomicieli.roundhouse.socials

/**
 * a group of social handlers
 * @param facebook a facebook handler
 * @param instagram an instagram handler
 * @param linkedin a linkedin handler
 * @param twitter a twitter handler
 * @param youtube a youtube handler
 */
data class Socials(
    val facebook: Handler? = null,
    val instagram: Handler? = null,
    val linkedin: Handler? = null,
    val twitter: Handler? = null,
    val youtube: Handler? = null
)

/**
 * A social network handler
 */
@JvmInline
value class Handler(val value: String)
