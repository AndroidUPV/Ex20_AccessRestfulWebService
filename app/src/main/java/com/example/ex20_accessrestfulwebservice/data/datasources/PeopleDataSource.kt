/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.data.datasources

import com.example.ex20_accessrestfulwebservice.model.People

/**
 * Interface declaring the methods that the DataSource exposes to Repositories.
 */
interface PeopleDataSource {
    /**
     * Gets a list of 10 randomly generated persons.
     */
    suspend fun getPeople(): Result<People>
}