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
package com.trenako.queries.pagination

/**
 * A {@code Page} of result which defines the start and the limit of results included.
 */
data class Page(val start: Int, val limit: Int) {
    init {
        require(start >= 0) { "Page starting index cannot be negative" }
        require(limit >= 0) { "Page limit cannot be negative" }
    }

    /**
     * Returns the next {@code Page} with the same {@code limit} of the this {@code Page}.
     * @return the next {@code Page}
     */
    fun next() = Page(limit + start, limit)

    /**
     * Returns the previous {@code Page} with the same {@code limit} of the this {@code Page}.
     *
     * In case there is no previous page, the returned page will be the first one (the page which starts at 0)
     *
     * @return the previous {@code Page}
     */
    fun previous(): Page {
        val newStart = if (start > limit) start - limit else 0
        return Page(newStart, limit)
    }

    companion object {
        /**
         * A default page
         */
        val DEFAULT_PAGE = Page(0, 25)
    }
}
