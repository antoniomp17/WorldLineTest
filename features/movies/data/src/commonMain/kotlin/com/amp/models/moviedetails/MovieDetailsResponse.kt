package com.amp.models.moviedetails

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("title")
    val title: String = "",

    @SerialName("overview")
    val overview: String = "",

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("vote_count")
    val voteCount: Int = 0,
    
    @SerialName("popularity")
    val popularity: Double = 0.0,
    
    @SerialName("original_language")
    val originalLanguage: String = "",
    
    @SerialName("original_title")
    val originalTitle: String = "",
    
    @SerialName("adult")
    val adult: Boolean = false,
    
    @SerialName("video")
    val video: Boolean = false,
    
    @SerialName("runtime")
    val runtime: Int? = null,
    
    @SerialName("budget")
    val budget: Int = 0,
    
    @SerialName("revenue")
    val revenue: Int = 0,
    
    @SerialName("homepage")
    val homepage: String? = null,
    
    @SerialName("imdb_id")
    val imdbId: String? = null,
    
    @SerialName("status")
    val status: String = "",
    
    @SerialName("tagline")
    val tagline: String? = null,
    
    @SerialName("belongs_to_collection")
    val belongsToCollection: String? = null,
    
    @SerialName("genres")
    val genres: List<GenreData> = emptyList(),
    
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyData> = emptyList(),
    
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryData> = emptyList(),
    
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageData> = emptyList()
)
