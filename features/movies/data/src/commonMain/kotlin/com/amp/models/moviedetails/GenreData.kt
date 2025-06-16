package com.amp.models.moviedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreData(
    @SerialName("id")
    val id: Int = 0,
    
    @SerialName("name")
    val name: String = ""
)