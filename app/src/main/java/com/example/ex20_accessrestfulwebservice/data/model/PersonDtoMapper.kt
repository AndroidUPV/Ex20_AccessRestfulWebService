/*
 * Copyright (c) 2022-2023 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package com.example.ex20_accessrestfulwebservice.data.model

import com.example.ex20_accessrestfulwebservice.model.Person

/**
 * Extension function to map a PersonDto instance to its domain counterpart.
 */
fun PersonDto.toDomain(): Person =
    Person(
        name = "${name.title} ${name.first} ${name.last}",
        address = "${location.street.number} ${location.street.number}",
        city = "${location.city}, ${location.state} ${location.postcode}",
        country = location.country,
        email = email,
        phone = cell,
        picture = picture.large
    )
