/*
 * Copyright (c) 2022-2024 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package com.example.ex20_accessrestfulwebservice.data.people

import com.example.ex20_accessrestfulwebservice.model.People

/**
 * Interface declaring the methods that the Repository exposes to ViewModels.
 */
interface PeopleRepository {
    /**
     * Returns a list of 10 randomly generated persons.
     */
    suspend fun getPeople(dataSource: ConnectionLibrary): Result<People>
}