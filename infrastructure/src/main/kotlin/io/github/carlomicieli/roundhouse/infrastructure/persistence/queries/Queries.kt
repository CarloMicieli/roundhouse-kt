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
package io.github.carlomicieli.roundhouse.infrastructure.persistence.queries

import io.github.carlomicieli.roundhouse.queries.pagination.Page
import io.github.carlomicieli.roundhouse.queries.sorting.Direction
import io.github.carlomicieli.roundhouse.queries.sorting.SortCriteria
import io.github.carlomicieli.roundhouse.queries.sorting.Sorting
import org.springframework.data.domain.Sort
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.CriteriaDefinition
import org.springframework.data.relational.core.query.Query

/**
 * Build a select query with pagination, sorting and a criteria
 * @param page the paging
 * @param sort the query sorting
 * @return a {@code Query}
 */
fun select(page: Page = Page.DEFAULT_PAGE, sort: Sorting = Sorting.DEFAULT_SORT): Query = select(page, sort) {
    Criteria.empty()
}

/**
 * Build a select query with pagination, sorting and a criteria
 * @param page the paging
 * @param sort the query sorting
 * @param criteriaSupplier the criteria supplier
 * @return a {@code Query}
 */
fun select(
    page: Page = Page.DEFAULT_PAGE,
    sort: Sorting = Sorting.DEFAULT_SORT,
    criteriaSupplier: () -> CriteriaDefinition
): Query {
    return Query.query(criteriaSupplier())
        .sort(sort.toSort())
        .limit(page.limit)
        .offset(page.start.toLong())
}

/**
 * Build a select query which returns a single result
 * @param criteriaSupplier the criteria supplier
 * @return a {@code Query}
 */
fun selectOne(criteriaSupplier: () -> CriteriaDefinition): Query = Query.query(criteriaSupplier())

private fun Sorting.toSort(): Sort {
    return if (this.size == 0) {
        Sort.unsorted()
    } else {
        val first = this.criteriaList.first()
        val others = this.criteriaList.drop(1)

        var output: Sort = Sort.by(first.toSortDirection(), first.propertyName)

        others.forEach {
            output = output.and(Sort.by(it.toSortDirection(), it.propertyName))
        }

        return output
    }
}

private fun SortCriteria.toSortDirection(): Sort.Direction = when (this.direction) {
    Direction.ASC -> Sort.Direction.ASC
    Direction.DESC -> Sort.Direction.DESC
}
