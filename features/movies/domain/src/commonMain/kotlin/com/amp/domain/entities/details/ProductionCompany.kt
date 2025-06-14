package com.amp.domain.entities.details

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
    val id: Int,
    val name: String,
    val logoPath: String?,
    val originCountry: String
)