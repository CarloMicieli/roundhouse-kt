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

import java.util.*

/**
 * an electric multiple unit rolling stock
 */
data class ElectricMultipleUnit(
    override val rollingStockId: UUID,
    override val railway: RollingStockRailway,
    override val category: RollingStockCategory,
    override val epoch: String,
    override val livery: String?,
    override val lengthOverBuffer: LengthOverBuffer?,
    override val technicalSpecifications: TechnicalSpecifications?,
    val typeName: String,
    val electricMultipleUnitType: ElectricMultipleUnitType,
    val isDummy: Boolean,
    val roadNumber: String?,
    val series: String?,
    val depot: String?,
    val dccInterface: DccInterface?,
    val control: Control?
) : RollingStock

enum class ElectricMultipleUnitType {
    DRIVING_CAR,
    MOTOR_CAR,
    POWER_CAR,
    TRAILER_CAR
}
