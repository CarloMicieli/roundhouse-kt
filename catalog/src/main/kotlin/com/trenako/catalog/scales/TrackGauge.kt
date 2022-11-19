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
package com.trenako.catalog.scales

/**
 * In rail transport, track gauge is the distance between the two rails of a railway track.
 * All vehicles on a rail network must have wheelsets that are compatible with the track gauge.
 *
 * Since many different track gauges exist worldwide, gauge differences often present a barrier to wider operation on
 * railway networks.
 */
enum class TrackGauge {
    /**
     * In modern usage, the term "broad gauge" generally refers to track spaced significantly wider than
     * 1,435 mm (4 ft 8+1⁄2 inches).
     *
     * Broad gauge is the dominant gauge in countries in Indian subcontinent, the former Soviet Union (CIS states,
     * Baltic states, Georgia and Ukraine), Mongolia and Finland, Spain, Portugal, Argentina, Chile and Ireland.
     * It is also use for the suburban railway systems in South Australia, and Victoria, Australia.
     */
    BROAD,

    /**
     * The term "medium gauge" had different meanings throughout history, depending on the local dominant gauge in use.
     */
    MEDIUM,

    /**
     * Very narrow gauges of under 2 feet (610 mm) were used for some industrial railways in space-restricted
     * environments such as mines or farms. The French company Decauville developed 500 mm (19+3⁄4 in) and
     * 400 mm (15+3⁄4 in) tracks, mainly for mines; Heywood developed 15 in (381 mm) gauge for estate railways.
     * The most common minimum-gauges were 15 in (381 mm),[15] 400 mm (15+3⁄4 in), 16 in (406 mm), 18 in (457 mm),
     * 500 mm (19+3⁄4 in) or 20 in (508 mm).
     */
    MINIMUM,

    /**
     * In modern usage, the term "narrow gauge" generally refers to track spaced significantly narrower than 1,435 mm
     * (4 ft 8+1⁄2 in).
     */
    NARROW,

    /**
     * In modern usage the term "standard gauge" refers to 1,435 mm (4 ft 8+1⁄2 inches).
     * Standard gauge is dominant in a majority of countries, including those in North America, most of western Europe,
     * North Africa and the Middle east, and in China.
     */
    STANDARD
}
