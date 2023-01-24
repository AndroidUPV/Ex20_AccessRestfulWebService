/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.model

import com.squareup.moshi.JsonClass

/**
 * A person object consisting of his/her name object, house location object, email address, cell phone number, and picture object.
 */
// The Moshi annotation @JsonClass generates an adapter to convert this class to/from JSON
@JsonClass(generateAdapter = true)
data class Person(
    val name: Name,
    val location: Location,
    val email: String,
    val cell: String,
    val picture: Picture,
)