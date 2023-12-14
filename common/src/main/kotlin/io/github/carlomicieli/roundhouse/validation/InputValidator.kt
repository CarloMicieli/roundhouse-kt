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
package io.github.carlomicieli.roundhouse.validation

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validator

class InputValidator<T>(private val validator: Validator) {
    /**
     * Validate the input
     * @param input the value to validate
     * @return the result of the validation
     */
    fun validate(input: T): Validated<T> {
        val errors =
            validator.validate(input)
                .map { it.toValidationError() }
                .sortedBy { it.fieldName }

        return if (errors.isEmpty()) {
            Validated.Valid(input)
        } else {
            Validated.Invalid(errors)
        }
    }

    private fun <T> ConstraintViolation<T>.toValidationError(): ValidationError {
        return ValidationError(this.propertyPath.toString(), this.message, this.invalidValue)
    }
}

fun <T> Validator.inputValidator(): InputValidator<T> = InputValidator(this)
