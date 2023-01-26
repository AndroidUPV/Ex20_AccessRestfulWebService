/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.di

import android.content.Context
import android.net.Uri
import com.example.ex20_accessrestfulwebservice.model.People
import com.example.ex20_accessrestfulwebservice.utils.ConnectivityChecker
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.URL

/**
 * Hilt module that determines how to provide required dependencies
 * for third party components and Builders.
 */
@Module
// The Hilt annotation @SingletonComponent creates and destroy instances following the lifecycle of the Application
@InstallIn(SingletonComponent::class)
class PeopleModule {

    /**
     * Provides an instance of Retrofit for the selected web service.
     */
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        // Base URL of the web service
        .baseUrl("https://randomuser.me/")
        // Uses Moshi to convert the JSON response into Kotlin objects
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    /**
     * Provides an instance of a URL to get 10 persons with random name, location,
     * email address, phone number, and picture in json format, and
     * excluding seed, result, page, and version data.
     */
    @Provides
    fun provideUrl() = URL(
        Uri.Builder()
            .scheme("https")
            .authority("randomuser.me")
            .appendPath("api")
            .appendQueryParameter("results", "10")
            .appendQueryParameter("inc", "name,location,email,cell,picture")
            .appendQueryParameter("format", "json")
            .appendQueryParameter("", "noinfo")
            .build()
            .toString()
    )

    /**
     * Provides an instance of a Moshi adapter to convert a People object from/to JSON.
     */
    @Provides
    fun provideMoshiAdapter(): JsonAdapter<People> = Moshi.Builder()
        .build()
        .adapter(People::class.java)

    /**
     * Provides an instance of a ConnectivityChecker.
     */
    @Provides
    fun provideConnectivityChecker(@ApplicationContext context: Context): ConnectivityChecker =
        ConnectivityChecker.getInstance(context)
}