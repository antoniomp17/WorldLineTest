package com.amp.models.moviedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguageData(
    @SerialName("english_name")
    val englishName: String = "",
    
    @SerialName("iso_639_1")
    val iso6391: String = "",
    
    @SerialName("name")
    val name: String = ""
)