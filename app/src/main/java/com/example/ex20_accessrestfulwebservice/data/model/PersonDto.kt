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

import com.squareup.moshi.JsonClass

/**
 * A person object consisting of his/her name object, house location object, email address, cell phone number, and picture object.
 */
// The Moshi annotation @JsonClass generates an adapter to convert this class to/from JSON
@JsonClass(generateAdapter = true)
data class PersonDto(
    val name: NameDto,
    val location: LocationDto,
    val email: String,
    val cell: String,
    val picture: PictureDto,
)