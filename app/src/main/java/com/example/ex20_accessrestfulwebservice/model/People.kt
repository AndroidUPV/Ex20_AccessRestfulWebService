/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * A people object consisting of a list of persons.
 */
// The Moshi annotation @JsonClass generates an adapter to convert this class to/from JSON
// The Moshi annotation @Json maps the incoming "results" json object to "people"
@JsonClass(generateAdapter = true)
data class People(@Json(name = "results") val people: List<Person>)
