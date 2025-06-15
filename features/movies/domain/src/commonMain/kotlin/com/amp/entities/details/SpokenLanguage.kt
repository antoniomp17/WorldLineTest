package com.amp.entities.details

import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguage(
    val englishName: String,
    val iso6391: String,
    val name: String
)