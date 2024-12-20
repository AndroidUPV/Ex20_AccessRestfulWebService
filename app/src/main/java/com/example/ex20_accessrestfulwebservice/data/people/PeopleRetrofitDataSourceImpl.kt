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

import com.example.ex20_accessrestfulwebservice.data.model.PeopleDto
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import javax.inject.Inject

/**
 * Data source that provides access to the Random User generator API using Retrofit.
 * It implements the PeopleDataSource interface.
 */
// @Inject enables Hilt to provide the required dependencies
class PeopleRetrofitDataSourceImpl @Inject constructor(
    retrofit: Retrofit
) : PeopleDataSource {

    // Creates the implementation of the API endpoint define by the interface
    private val retrofitPeopleService = retrofit.create(PeopleRetrofitInterface::class.java)

    /**
     * Interface defining the endpoint that provides 10 randomly generated persons.
     */
    interface PeopleRetrofitInterface {
        /**
         * Returns a list of 10 randomly generated persons with random name, location,
         * email address, phone number, and picture, and excluding seed,
         * result, page, and version data. It is automatically converted from JSON to People.
         */
        // The retrofit annotation @GET provides the query to be used with the base URL
        // The Retrofit annotation @Query enables dynamic parameters in the query
        @GET("api?inc=name,location,email,cell,picture&noinfo&format=json")
        suspend fun getPeople(@Query("results") number: Int): Response<PeopleDto>
    }

    /**
     * Returns 10 randomly generated person using Retrofit or
     * IOException if something goes wrong.
     */
    override suspend fun getPeople(): Result<PeopleDto> {
        // Suspend functions for Retrofit interfaces are main-safe
        val people = retrofitPeopleService.getPeople(10)
        return if (people.isSuccessful) {
            Result.success(people.body() as PeopleDto)
        } else {
            Result.failure(IOException(people.errorBody().toString()))
        }
    }
}