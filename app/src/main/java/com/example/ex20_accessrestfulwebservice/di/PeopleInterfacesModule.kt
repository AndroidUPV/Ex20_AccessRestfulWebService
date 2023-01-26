/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.di

import com.example.ex20_accessrestfulwebservice.data.datasources.PeopleDataSource
import com.example.ex20_accessrestfulwebservice.data.datasources.PeopleRetrofitDataSourceImpl
import com.example.ex20_accessrestfulwebservice.data.datasources.PeopleUrlConnectionDataSourceImpl
import com.example.ex20_accessrestfulwebservice.data.repositories.PeopleRepository
import com.example.ex20_accessrestfulwebservice.data.repositories.PeopleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

/**
 * Hilt module that determines how to provide required dependencies for interfaces.
 */
@Module
// The Hilt annotation @SingletonComponent creates and destroy instances following the lifecycle of the Application
@InstallIn(SingletonComponent::class)
abstract class PeopleInterfacesModule {

    // New annotations defined to distinguish between different implementation of the same interface
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RetrofitImpl

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UrlConnectionImpl

    /**
     * Provides an instance of a PeopleRetrofitDataSource.
     */
    @Binds
    @RetrofitImpl
    abstract fun bindPeopleRetrofitDataSource(
        peopleRetrofitDataSourceImpl: PeopleRetrofitDataSourceImpl
    ): PeopleDataSource

    /**
     * Binds an instance of a PeopleUrlConnectionDataSource.
     */
    @Binds
    @UrlConnectionImpl
    abstract fun bindUrlConnectionRetrofitDataSource(
        peopleUrlConnectionDataSourceImpl: PeopleUrlConnectionDataSourceImpl
    ): PeopleDataSource

    /**
     * Provides an instance of a PeopleRepository.
     */
    @Binds
    abstract fun bindPeopleRepository(
        peopleRepositoryImpl: PeopleRepositoryImpl
    ): PeopleRepository

}