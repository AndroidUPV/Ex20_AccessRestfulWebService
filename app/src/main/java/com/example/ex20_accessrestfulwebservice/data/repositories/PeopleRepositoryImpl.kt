/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.data.repositories

import com.example.ex20_accessrestfulwebservice.data.datasources.PeopleRetrofitDataSourceImpl
import com.example.ex20_accessrestfulwebservice.data.datasources.UrlConnectionPeopleDataSourceImpl
import com.example.ex20_accessrestfulwebservice.model.People
import com.example.ex20_accessrestfulwebservice.utils.ConnectivityChecker
import com.example.ex20_accessrestfulwebservice.utils.NoInternetException

/**
 * Enumeration for the available HTTP connection libraries.
 */
enum class ConnectionLibrary { RETROFIT, HTTPS_URL_CONNECTION }

/**
 * Repository for retrieving lists of randomly generated persons.
 * It implements the PeopleRepository interface and the Singleton pattern.
 */
class PeopleRepositoryImpl private constructor(
    private val connectivityChecker: ConnectivityChecker
) : PeopleRepository {

    // @Volatile and synchronized() ensure that this Singleton creation is thread-safe
    companion object {
        @Volatile
        private lateinit var instance: PeopleRepositoryImpl

        fun getInstance(connectivityChecker: ConnectivityChecker): PeopleRepositoryImpl {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = PeopleRepositoryImpl(connectivityChecker)
                }
                return instance
            }
        }
    }

    /**
     * Returns a list of 10 randomly generated persons.
     * The information is retrieved using the selected HTTP connection library.
     */
    override suspend fun getPeople(dataSource: ConnectionLibrary): Result<People> {
        return if (connectivityChecker.isConnectionAvailable()) {
            // Return the list of people from the data source if there is Internet connection
            when (dataSource) {
                ConnectionLibrary.RETROFIT ->
                    PeopleRetrofitDataSourceImpl.getPeople()
                ConnectionLibrary.HTTPS_URL_CONNECTION ->
                    UrlConnectionPeopleDataSourceImpl.getPeople()
            }
        } else {
            // Return a failed result if there is no Internet connection
            Result.failure(NoInternetException())
        }
    }

}