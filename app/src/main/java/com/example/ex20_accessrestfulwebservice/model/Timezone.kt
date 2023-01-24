/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.model

import com.squareup.moshi.JsonClass

/**
 * A timezone object consisting of its offset and description.
 */
// The Moshi annotation @JsonClass generates an adapter to convert this class to/from JSON
@JsonClass(generateAdapter = true)
data class Timezone(val offset: String, val description: String)
