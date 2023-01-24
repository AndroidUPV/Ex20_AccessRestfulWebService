/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package com.example.ex20_accessrestfulwebservice.model

import com.squareup.moshi.JsonClass

/**
 * A picture object consisting of the URL to access the person's picture in large, medium, and thumbnail sizes.
 */
// The Moshi annotation @JsonClass generates an adapter to convert this class to/from JSON
@JsonClass(generateAdapter = true)
data class Picture(val large: String, val medium: String, val thumbnail: String)