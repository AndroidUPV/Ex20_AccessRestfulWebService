/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.data.repositories

import com.example.ex20_accessrestfulwebservice.data.datasources.PeopleDataSource
import com.example.ex20_accessrestfulwebservice.di.PeopleBinderModule
import com.example.ex20_accessrestfulwebservice.model.People
import com.example.ex20_accessrestfulwebservice.utils.ConnectivityChecker
import com.example.ex20_accessrestfulwebservice.utils.NoInternetException
import javax.inject.Inject

/**
 * Enumeration for the available HTTP connection libraries.
 */
enum class ConnectionLibrary { RETROFIT, HTTPS_URL_CONNECTION }

/**
 * Repository for retrieving lists of randomly generated persons.
 * It implements the PeopleRepository interface.
 */
// @Inject enables Hilt to provide the required dependencies
class PeopleRepositoryImpl @Inject constructor(
    private val connectivityChecker: ConnectivityChecker,
    @PeopleBinderModule.RetrofitImpl private val retrofitDataSource: PeopleDataSource,
    @PeopleBinderModule.UrlConnectionImpl private val urlConnectionDataSource: PeopleDataSource
) : PeopleRepository {

    /**
     * Returns a list of 10 randomly generated persons.
     * The information is retrieved using the selected HTTP connection library.
     */
    override suspend fun getPeople(dataSource: ConnectionLibrary): Result<People> {
        return if (connectivityChecker.isConnectionAvailable()) {
            // Return the list of people from the data source if there is Internet connection
            when (dataSource) {
                ConnectionLibrary.RETROFIT ->
                    retrofitDataSource.getPeople()
                ConnectionLibrary.HTTPS_URL_CONNECTION ->
                    urlConnectionDataSource.getPeople()
            }
        } else {
            // Return a failed result if there is no Internet connection
            Result.failure(NoInternetException())
        }
    }

}