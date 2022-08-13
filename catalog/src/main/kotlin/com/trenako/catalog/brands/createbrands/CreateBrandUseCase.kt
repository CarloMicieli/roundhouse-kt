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
package com.trenako.catalog.brands.createbrands

import com.trenako.usecases.UseCase
import com.trenako.usecases.UseCaseResult
import com.trenako.validation.Validated
import com.trenako.validation.inputValidator
import java.time.LocalDateTime
import javax.validation.Validator

class CreateBrandUseCase(private val validator: Validator, private val repository: CreateBrandRepository) : UseCase<CreateBrand, BrandCreated, CreateBrandError> {
    override suspend fun execute(input: CreateBrand): UseCaseResult<BrandCreated, CreateBrandError> {
        val inputValidator = validator.inputValidator<CreateBrand>()
        return when (val result = inputValidator.validate(input)) {
            is Validated.Valid -> doWork(result.value)
            is Validated.Invalid -> UseCaseResult.withError(CreateBrandError.InvalidRequest(result.errors))
        }
    }

    private suspend fun doWork(input: CreateBrand): UseCaseResult<BrandCreated, CreateBrandError> {
        if (repository.exists(input.name)) {
            return UseCaseResult.withError(CreateBrandError.BrandAlreadyExists(input.name))
        }

        val newBrand = input.toNewBrand()
        repository.insert(newBrand)
        return UseCaseResult.withOutput(BrandCreated(newBrand.id, LocalDateTime.now()))
    }

    private fun CreateBrand.toNewBrand(): CreateBrandRepository.NewBrand {
        return CreateBrandRepository.NewBrand(
            id = BrandId(this.name),
            name = this.name
        )
    }
}
