package com.amp.models.moviedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanyData(
    @SerialName("id")
    val id: Int = 0,
    
    @SerialName("name")
    val name: String = "",
    
    @SerialName("logo_path")
    val logoPath: String? = null,
    
    @SerialName("origin_country")
    val originCountry: String = ""
)