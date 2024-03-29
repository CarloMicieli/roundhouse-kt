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
package io.github.carlomicieli.roundhouse.catalog.scales.createscales

import io.github.carlomicieli.roundhouse.catalog.scales.Gauge
import io.github.carlomicieli.roundhouse.catalog.scales.Ratio
import io.github.carlomicieli.roundhouse.catalog.scales.ScaleId
import io.github.carlomicieli.roundhouse.catalog.scales.Standard
import io.github.carlomicieli.roundhouse.catalog.scales.TrackGauge
import io.github.carlomicieli.roundhouse.lengths.MeasureUnit
import io.github.carlomicieli.roundhouse.lengths.TwoLengths
import io.github.carlomicieli.roundhouse.usecases.UseCase
import io.github.carlomicieli.roundhouse.usecases.UseCaseResult
import io.github.carlomicieli.roundhouse.util.EnumUtils.toEnum
import io.github.carlomicieli.roundhouse.validation.Validated
import io.github.carlomicieli.roundhouse.validation.inputValidator
import jakarta.validation.Validator
import java.time.Instant

class CreateScaleUseCase(private val validator: Validator, private val repository: CreateScaleRepository) :
    UseCase<CreateScale, ScaleCreated, CreateScaleError> {
    private val twoLengths = TwoLengths(MeasureUnit.MILLIMETERS, MeasureUnit.INCHES)

    override suspend fun execute(input: CreateScale): UseCaseResult<ScaleCreated, CreateScaleError> {
        val inputValidator = validator.inputValidator<CreateScale>()
        return when (val result = inputValidator.validate(input)) {
            is Validated.Valid -> doWork(result.value)
            is Validated.Invalid -> UseCaseResult.withError(CreateScaleError.InvalidRequest(result.errors))
        }
    }

    private suspend fun doWork(input: CreateScale): UseCaseResult<ScaleCreated, CreateScaleError> {
        if (repository.exists(input.name)) {
            return UseCaseResult.withError(CreateScaleError.ScaleAlreadyExists(input.name))
        }

        val newScale = input.toNewScale()
        repository.insert(newScale)
        return UseCaseResult.withOutput(ScaleCreated(newScale.id, Instant.now()))
    }

    private fun CreateScale.toNewScale(): NewScale =
        NewScale(
            id = ScaleId.of(this.name),
            name = this.name,
            ratio = Ratio.of(this.ratio),
            gauge = this.gauge?.toGauge() ?: throw IllegalArgumentException("invalid scale gauge"),
            description = this.description,
            standards = this.standards.map { Standard.valueOf(it) }.toSet()
        )

    private fun CreateScaleGauge.toGauge(): Gauge {
        val trackGauge: TrackGauge =
            this.trackGauge?.toEnum<TrackGauge>() ?: throw IllegalArgumentException(
                "track gauge is required"
            )
        return twoLengths(this.millimeters?.toDouble(), this.inches?.toDouble())?.let {
            Gauge(
                it.first,
                it.second,
                trackGauge
            )
        } ?: throw IllegalArgumentException("invalid scale gauge")
    }
}
