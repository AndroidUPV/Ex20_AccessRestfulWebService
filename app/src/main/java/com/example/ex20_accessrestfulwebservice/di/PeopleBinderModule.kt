/*
 * Copyright (c) 2022-2023 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package com.example.ex20_accessrestfulwebservice.di

import com.example.ex20_accessrestfulwebservice.data.people.PeopleDataSource
import com.example.ex20_accessrestfulwebservice.data.people.PeopleRetrofitDataSourceImpl
import com.example.ex20_accessrestfulwebservice.data.people.PeopleUrlConnectionDataSourceImpl
import com.example.ex20_accessrestfulwebservice.data.people.PeopleRepository
import com.example.ex20_accessrestfulwebservice.data.people.PeopleRepositoryImpl
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
abstract class PeopleBinderModule {

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