/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.model

data class Person(
    val name: Name,
    val location: Location,
    val email: String,
    val cell: String,
    val picture: Picture,
    val nat: String
)