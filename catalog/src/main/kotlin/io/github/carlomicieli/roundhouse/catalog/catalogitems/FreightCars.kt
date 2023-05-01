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

import java.util.UUID

/**
 * a freight car rolling stock
 */
data class FreightCar(
    override val rollingStockId: UUID,
    override val railway: RollingStockRailway,
    override val category: RollingStockCategory,
    override val epoch: String,
    override val livery: String?,
    override val lengthOverBuffer: LengthOverBuffer?,
    override val technicalSpecifications: TechnicalSpecifications?,
    val typeName: String,
    val roadNumber: String?,
    val freightCarType: FreightCarType?
) : RollingStock

/**
 * the freight car type
 */
enum class FreightCarType {
    AUTO_TRANSPORT_CARS,
    BRAKE_WAGON,
    CONTAINER_CARS,
    COVERED_FREIGHT_CARS,
    DEEP_WELL_FLAT_CARS,
    DUMP_CARS,
    GONDOLA,
    HEAVY_GOODS_WAGONS,
    HINGED_COVER_WAGONS,
    HOPPER_WAGON,
    REFRIGERATOR_CARS,
    SILO_CONTAINER_CARS,
    SLIDE_TARPAULIN_WAGON,
    SLIDING_WALL_BOXCARS,
    SPECIAL_TRANSPORT,
    STAKE_WAGONS,
    SWING_ROOF_WAGON,
    TANK_CARS,
    TELESCOPE_HOOD_WAGONS
}
