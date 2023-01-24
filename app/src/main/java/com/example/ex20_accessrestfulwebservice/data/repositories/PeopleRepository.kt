/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.data.repositories

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