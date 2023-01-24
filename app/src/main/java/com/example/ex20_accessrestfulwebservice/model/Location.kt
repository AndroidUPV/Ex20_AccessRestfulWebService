/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.model

import com.squareup.moshi.JsonClass

/**
 * A house location object consisting of a street object, city name, state name, country name, postal code, coordinates object and timezone object.
 */
// The Moshi annotation @JsonClass generates an adapter to convert this class to/from JSON
@JsonClass(generateAdapter = true)
data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
)
