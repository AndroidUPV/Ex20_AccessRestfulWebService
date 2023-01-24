/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.di

import android.content.Context
import com.example.ex20_accessrestfulwebservice.data.repositories.PeopleRepository
import com.example.ex20_accessrestfulwebservice.data.repositories.PeopleRepositoryImpl
import com.example.ex20_accessrestfulwebservice.utils.ConnectivityChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Hilt module that determines how to provide required dependencies.
 */
@Module
// The Hilt annotation @ViewModelScope creates and destroy instances following the lifecycle of the ViewModel
@InstallIn(ViewModelComponent::class)
class PeopleModule {

    /**
     * Provides an instance of a ConnectivityChecker.
     */
    // The Hilt annotation @ViewModelScoped provides the same instance of the class throughout the lifetime of the ViewModel
    @ViewModelScoped
    @Provides
    fun provideConnectivityChecker(@ApplicationContext context: Context): ConnectivityChecker =
        ConnectivityChecker.getInstance(context)

    /**
     * Provides an instance of a PeopleRepository.
     */
    @ViewModelScoped
    // The Hilt annotation @ViewModelScoped provides the same instance of the class throughout the lifetime of the ViewModel
    @Provides
    fun providePeopleRepository(connectivityChecker: ConnectivityChecker): PeopleRepository =
        PeopleRepositoryImpl.getInstance(connectivityChecker)
}