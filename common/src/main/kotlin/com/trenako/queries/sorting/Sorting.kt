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
package com.trenako.queries.sorting

/**
 * It represents the sorting criteria for {@code Query}s.
 */
class Sorting private constructor(val criteriaList: List<SortCriteria>) {

    /**
     * Return the number of criteria defined in this {@code Sorting}
     */
    val size: Int = criteriaList.size

    /**
     * Returns the sorting criteria for this property name, if the property is not included in the sorting
     * this method will throw a {@code NoSuchElementException}.
     * @param propertyName the property name
     * @return a {@code SortCriteria}
     */
    operator fun get(propertyName: String): SortCriteria? = criteriaList.firstOrNull { it.propertyName.equals(propertyName, true) }

    companion object {
        val DEFAULT_SORT = Sorting(emptyList())

        /**
         * Create a new {@code Sorting} builder, adding the first sorting criteria
         * @param propertyName the property name to add
         * @param direction the sorting direction
         * @return a sorting builder
         */
        fun by(propertyName: String, direction: Direction = Direction.ASC): Builder {
            val list = mutableListOf<SortCriteria>()
            list.add(SortCriteria(propertyName, direction))
            return Builder(list)
        }
    }

    class Builder internal constructor(private val list: MutableList<SortCriteria>) {
        /**
         * Add this sorting criteria
         * @param propertyName the property name to add
         * @param direction the sorting direction
         * @return a sorting builder
         */
        fun andThenBy(propertyName: String, direction: Direction = Direction.ASC): Builder {
            list.add(SortCriteria(propertyName, direction))
            return this
        }

        /**
         * Build a sorting from this builder
         * @return a {@code Sorting} object
         */
        fun build(): Sorting = Sorting(list)
    }
}
