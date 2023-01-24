/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.data.datasources

import com.example.ex20_accessrestfulwebservice.model.People
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.io.IOException

/**
 * Data source that provides access to the Random User generator API using Retrofit.
 * It implements the PeopleDataSource interface and the Singleton pattern.
 */
// Just by declaring object instead of class we have a Singleton. Only valid for empty constructors.
object PeopleRetrofitDataSourceImpl : PeopleDataSource {

    // Creates the implementation of the API endpoint defined by the interface PeopleRetrofitInterface
    private val retrofitService =
        Retrofit.Builder()
            // Base URL of the web service
            .baseUrl("https://randomuser.me/api/")
            // Uses Moshi to convert the JSON response into a People object
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PeopleRetrofitInterface::class.java)

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
        @GET("?results=10&inc=name,location,email,cell,picture&noinfo&format=json")
        suspend fun getPeople(): Response<People>
    }

    /**
     * Returns 10 randomly generated person using Retrofit or
     * IOException if something goes wrong.
     */
    override suspend fun getPeople(): Result<People> {
        // Suspend functions for Retrofit interfaces are main-safe
        val people = retrofitService.getPeople()
        return if (people.isSuccessful) {
            Result.success(people.body() as People)
        } else {
            Result.failure(IOException(people.errorBody().toString()))
        }
    }
}