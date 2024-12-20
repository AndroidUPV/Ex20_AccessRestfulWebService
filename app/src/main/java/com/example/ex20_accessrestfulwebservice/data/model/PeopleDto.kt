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

package com.example.ex20_accessrestfulwebservice.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * A people object consisting of a list of persons.
 */
// The Moshi annotation @JsonClass generates an adapter to convert this class to/from JSON
// The Moshi annotation @Json maps the incoming "results" json object to "people"
@JsonClass(generateAdapter = true)
data class PeopleDto(@Json(name = "results") val people: List<PersonDto>)
