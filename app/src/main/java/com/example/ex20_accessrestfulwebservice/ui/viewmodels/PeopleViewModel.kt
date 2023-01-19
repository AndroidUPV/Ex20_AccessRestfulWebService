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
import com.example.ex20_accessrestfulwebservice.model.Person

class PeopleViewModel : ViewModel() {

    private val _people = MutableLiveData(listOf<Person>())
    val people: LiveData<List<Person>> = _people


}