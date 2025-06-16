package com.amp.entities.details

import kotlinx.serialization.Serializable

@Serializable
data class Collection(
    val id: Int = 0,
    val name: String = "",
    val posterPath: String? = null,
    val backdropPath: String? = null
)