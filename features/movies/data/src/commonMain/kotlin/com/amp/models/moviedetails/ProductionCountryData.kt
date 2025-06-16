package com.amp.models.moviedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryData(
    @SerialName("iso_3166_1")
    val iso31661: String = "",
    
    @SerialName("name")
    val name: String = ""
)