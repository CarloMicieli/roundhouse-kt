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
package com.trenako.queries.result

import com.trenako.queries.errors.QueryError
import java.lang.Exception

/**
 * A query result: either a single value or an error
 */
sealed interface SingleResult<T> {
    data class Result<T>(val value: T?) : SingleResult<T>
    data class Error<T>(val queryError: QueryError) : SingleResult<T>
}

fun <T> T?.toSingleResult(): SingleResult<T> = SingleResult.Result(this)

fun <T> Exception.toQueryError(): SingleResult<T> = SingleResult.Error(QueryError.DatabaseError(this))