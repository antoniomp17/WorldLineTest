package com.amp.entities.details

import kotlinx.serialization.Serializable


@Serializable
data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val popularity: Double = 0.0,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val adult: Boolean = false,
    val video: Boolean = false,
    val runtime: Int? = null,
    val budget: Int = 0,
    val revenue: Int = 0,
    val homepage: String? = null,
    val imdbId: String? = null,
    val status: String = "",
    val tagline: String? = null,
    val belongsToCollection: Collection? = null,
    val genres: List<Genre> = emptyList(),
    val productionCompanies: List<ProductionCompany> = emptyList(),
    val productionCountries: List<ProductionCountry> = emptyList(),
    val spokenLanguages: List<SpokenLanguage> = emptyList()
)
