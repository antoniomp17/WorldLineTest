package com.amp.domain.entities.details

import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val originalLanguage: String,
    val originalTitle: String,
    val adult: Boolean,
    val video: Boolean,
    val runtime: Int?,
    val budget: Int,
    val revenue: Int,
    val homepage: String?,
    val imdbId: String?,
    val status: String,
    val tagline: String?,
    val belongsToCollection: String?,
    val genres: List<Genre>,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val spokenLanguages: List<SpokenLanguage>
)
