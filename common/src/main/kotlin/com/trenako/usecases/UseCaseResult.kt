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
package com.trenako.usecases

/**
 * A {@code UseCase} result: either an output or an error.
 */
sealed class UseCaseResult<out R, out E> private constructor() {
    abstract fun isError(): Boolean

    data class Output<out R>(val value: R) : UseCaseResult<R, Nothing>() {
        override fun isError(): Boolean = false
    }

    data class Error<out E>(val value: E) : UseCaseResult<Nothing, E>() {
        override fun isError(): Boolean = true
    }

    companion object {
        /**
         * Create a new {@code UseCaseResult} with the given value
         * @param value the output value
         * @return a {@code UseCaseResult} output
         */
        fun <T> withOutput(value: T): UseCaseResult<T, Nothing> = Output(value)

        /**
         * Create a new {@code UseCaseResult} with the given error
         * @param error the error value
         * @return a {@code UseCaseResult} error
         */
        fun <E> withError(error: E): UseCaseResult<Nothing, E> = Error(error)
    }
}

inline fun <R, E, R2> UseCaseResult<R, E>.map(f: (R) -> R2): UseCaseResult<R2, E> = when (this) {
    is UseCaseResult.Output -> UseCaseResult.Output(f(this.value))
    is UseCaseResult.Error -> this
}

inline fun <R, E, R2> UseCaseResult<R, E>.flatMap(f: (R) -> UseCaseResult<R2, E>) = when (this) {
    is UseCaseResult.Output -> f(this.value)
    is UseCaseResult.Error -> this
}

inline fun <R, E, E2> UseCaseResult<R, E>.mapError(f: (E) -> E2): UseCaseResult<R, E2> = when (this) {
    is UseCaseResult.Output -> this
    is UseCaseResult.Error -> UseCaseResult.Error(f(this.value))
}

fun <R> UseCaseResult<R, R>.get(): R = when (this) {
    is UseCaseResult.Error -> this.value
    is UseCaseResult.Output -> this.value
}
