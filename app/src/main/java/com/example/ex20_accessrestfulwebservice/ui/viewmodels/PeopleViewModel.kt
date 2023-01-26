/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ex20_accessrestfulwebservice.data.repositories.ConnectionLibrary
import com.example.ex20_accessrestfulwebservice.data.repositories.PeopleRepository
import com.example.ex20_accessrestfulwebservice.model.People
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Holds information about the list of persons.
 */
// The Hilt annotation @HiltEntryPoint is required to receive dependencies from its parent class
@HiltViewModel
// @Inject enables Hilt to provide the required dependencies
class PeopleViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository
) : ViewModel() {

    // Backing property for the list of persons (default empty)
    private val _people = MutableLiveData<People>()

    // List of persons
    val people: LiveData<People> = _people

    // Backing property for the error to be notified (default null)
    private val _error = MutableLiveData<Throwable?>(null)

    // Error to be notified
    val error: LiveData<Throwable?> = _error

    // Backing property for the connection library ti be used (default Retrofit)
    private val _connectionLibrary = MutableLiveData(ConnectionLibrary.RETROFIT)

    // Connection library to be used
    val connectionLibrary: LiveData<ConnectionLibrary> = _connectionLibrary

    // Get a list of 10 random people when creating the ViewModel
    init {
        getPeople()
    }

    /**
     * Retrieves a list of 10 random persons using the selected HTTP connection library,
     * and adds it to the current list of persons.
     */
    fun getMorePeople() {
        // As it is a blocking operation it should be executed in a thread
        viewModelScope.launch {
            peopleRepository
                // Get the result from the repository
                .getPeople(_connectionLibrary.value ?: ConnectionLibrary.RETROFIT)
                // Check the result
                .fold(
                    onSuccess = { result ->
                        // Update the list of persons if the operation was successful
                        _people.value =
                            People(_people.value?.people?.plus(result.people) ?: listOf())
                    },
                    onFailure = { throwable ->
                        // Update the error to be notified to the user in there was any problem
                        _error.value = throwable
                    }
                )
        }
    }

    /**
     * Retrieves a list of 10 random persons using the selected HTTP connection library.
     */
    private fun getPeople() {
        // As it is a blocking operation it should be executed in a thread
        viewModelScope.launch {
            peopleRepository
                // Get the result from the repository
                .getPeople(_connectionLibrary.value ?: ConnectionLibrary.RETROFIT)
                // Check the result
                .fold(
                    onSuccess = { people ->
                        // Update the list of persons if the operation was successful
                        _people.value = people
                    },
                    onFailure = { throwable ->
                        // Update the error to be notified to the user in there was any problem
                        _error.value = throwable
                    }
                )
        }
    }

    /**
     * Cancels any existing error as its message has already been displayed.
     */
    fun resetError() {
        _error.value = null
    }

    /**
     * Sets the selected HTTP connection library.
     */
    fun setConnectionLibrary(library: ConnectionLibrary) {
        _connectionLibrary.value = library
    }
}