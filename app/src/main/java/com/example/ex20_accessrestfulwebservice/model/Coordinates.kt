/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.model

import com.squareup.moshi.JsonClass

/**
 * A coordinates object consisting of its latitude and longitude.
 */
// The Moshi annotation @JsonClass generates an adapter to convert this class to/from JSON
@JsonClass(generateAdapter = true)
data class Coordinates(val latitude: Float, val longitude: Float)
