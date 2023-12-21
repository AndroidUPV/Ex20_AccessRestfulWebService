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

package com.example.ex20_accessrestfulwebservice.data.people

import com.example.ex20_accessrestfulwebservice.data.model.PeopleDto

/**
 * Interface declaring the methods that the DataSource exposes to Repositories.
 */
interface PeopleDataSource {
    /**
     * Gets a list of 10 randomly generated persons.
     */
    suspend fun getPeople(): Result<PeopleDto>
}