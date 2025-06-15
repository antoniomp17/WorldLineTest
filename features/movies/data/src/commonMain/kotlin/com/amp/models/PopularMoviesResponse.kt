package com.amp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesResponse(

    @SerialName("page")
    val page: Int = 0,

    @SerialName("results")
    val results: List<MovieData> = emptyList(),

    @SerialName("total_pages")
    val totalPages: Int = 0,

    @SerialName("total_results")
    val totalResults: Int = 0
)