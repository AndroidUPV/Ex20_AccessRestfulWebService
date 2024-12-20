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

package com.example.ex20_accessrestfulwebservice.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ex20_accessrestfulwebservice.data.people.ConnectionLibrary
import com.example.ex20_accessrestfulwebservice.data.people.PeopleRepository
import com.example.ex20_accessrestfulwebservice.model.People
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    // Backing property for the list of persons
    private val _people = MutableStateFlow(People(listOf()))

    // List of persons
    val people = _people.asStateFlow()

    // Backing property for the error to be notified (default null)
    private val _error = MutableStateFlow<Throwable?>(null)

    // Error to be notified
    val error = _error.asStateFlow()

    // Backing property for the connection library to be used (default Retrofit)
    private val _connectionLibrary = MutableStateFlow(ConnectionLibrary.RETROFIT)

    // Connection library to be used
    val connectionLibrary = _connectionLibrary.asStateFlow()

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
                .getPeople(_connectionLibrary.value)
                // Check the result
                .fold(
                    onSuccess = { result ->
                        // Update the list of persons if the operation was successful
                        _people.update { currentPeople ->
                            People(currentPeople.people.plus(result.people))
                        }
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
                .getPeople(_connectionLibrary.value)
                // Check the result
                .fold(
                    onSuccess = { newPeople ->
                        _people.update {
                            // Update the list of persons if the operation was successful
                            newPeople
                        }
                    },
                    onFailure = { throwable ->
                        _error.update {
                            // Update the error to be notified to the user in there was any problem
                            throwable
                        }
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