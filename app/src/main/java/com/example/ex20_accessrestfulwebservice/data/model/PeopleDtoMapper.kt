/*
 * Copyright (c) 2022-2023 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package com.example.ex20_accessrestfulwebservice.data.model

import com.example.ex20_accessrestfulwebservice.model.People

/**
 * Extension function to map a PeopleDto instance to its domain counterpart.
 */
fun PeopleDto.toDomain(): People =
    People(
        people.map { personDto ->
            personDto.toDomain()
        }
    )

/**
 * Extension function to map a Result<PeopleDto> instance to its domain counterpart.
 */
fun Result<PeopleDto>.toDomain() =
    fold(
        onSuccess = {peopleDto ->
            Result.success(peopleDto.toDomain())
        },
        onFailure = {throwable ->
            Result.failure(throwable)
        }

    )