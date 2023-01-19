/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.data.repositories

import com.example.ex20_accessrestfulwebservice.data.datasources.PeopleRetrofitDataSource
import com.example.ex20_accessrestfulwebservice.model.Person

object PeopleRepository {

    suspend fun getPeople(): List<Person> =
        PeopleRetrofitDataSource.getInstance().getPeople()

}