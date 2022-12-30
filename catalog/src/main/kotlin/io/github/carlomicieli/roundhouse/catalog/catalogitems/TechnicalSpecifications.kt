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
package io.github.carlomicieli.roundhouse.catalog.catalogitems

/**
 * The technical specification for a rolling stock
 */
data class TechnicalSpecifications(
    val minimumRadius: Float?,
    val coupling: Coupling?,
    val flywheelFitted: FeatureFlag?,
    val metalBody: FeatureFlag?,
    val interiorLights: FeatureFlag?,
    val lights: FeatureFlag?,
    val springBuffers: FeatureFlag?
)

/**
 * a feature flag for rolling stock technical specifications
 */
enum class FeatureFlag {
    YES,
    NO,
    NA
}

data class Coupling(
    val socket: Socket,
    val closeCouplers: FeatureFlag?,
    val digitalShunting: FeatureFlag?
)

enum class Socket {
    NONE,
    NEM_355,
    NEM_356,
    NEM_357,
    NEM_359,
    NEM_360,
    NEM_362,
    NEM_365
}
