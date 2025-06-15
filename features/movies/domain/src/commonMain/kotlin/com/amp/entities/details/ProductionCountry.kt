package com.amp.entities.details

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry(
    val iso31661: String,
    val name: String
)