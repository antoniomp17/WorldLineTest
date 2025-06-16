package com.amp.models.moviedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionData(
    @SerialName("id")
    val id: Int = 0,
    
    @SerialName("name")
    val name: String = "",
    
    @SerialName("poster_path")
    val posterPath: String? = null,
    
    @SerialName("backdrop_path")
    val backdropPath: String? = null
)
