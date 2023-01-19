/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.data.datasources

import com.example.ex20_accessrestfulwebservice.model.Person
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object PeopleRetrofitDataSource {

    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://randomuser.me/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getInstance() = retrofit.create(PeopleService::class.java)

    interface PeopleService {
        @GET("results=10&inc=name,location,email,cell,picture,nat&noinfo&format=json")
        suspend fun getPeople(): List<Person>
    }
}