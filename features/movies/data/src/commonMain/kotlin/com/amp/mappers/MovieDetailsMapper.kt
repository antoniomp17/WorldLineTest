package com.amp.mappers

import com.amp.models.moviedetails.MovieDetailsResponse
import com.amp.entities.details.MovieDetails
import com.amp.models.moviedetails.GenreData
import com.amp.models.moviedetails.ProductionCompanyData
import com.amp.models.moviedetails.ProductionCountryData
import com.amp.models.moviedetails.SpokenLanguageData
import com.amp.models.moviedetails.CollectionData

fun MovieDetailsResponse.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        adult = adult,
        video = video,
        runtime = runtime,
        budget = budget,
        revenue = revenue,
        homepage = homepage,
        imdbId = imdbId,
        status = status,
        tagline = tagline,
        belongsToCollection = belongsToCollection?.toDomain(),
        genres = genres.map { it.toDomain() },
        productionCompanies = productionCompanies.map { it.toDomain() },
        productionCountries = productionCountries.map { it.toDomain() },
        spokenLanguages = spokenLanguages.map { it.toDomain() }
    )
}

private fun GenreData.toDomain(): com.amp.entities.details.Genre {
    return com.amp.entities.details.Genre(
        id = id,
        name = name
    )
}

private fun ProductionCompanyData.toDomain(): com.amp.entities.details.ProductionCompany {
    return com.amp.entities.details.ProductionCompany(
        id = id,
        name = name,
        logoPath = logoPath,
        originCountry = originCountry
    )
}

private fun ProductionCountryData.toDomain(): com.amp.entities.details.ProductionCountry {
    return com.amp.entities.details.ProductionCountry(
        iso31661 = iso31661,
        name = name
    )
}

private fun SpokenLanguageData.toDomain(): com.amp.entities.details.SpokenLanguage {
    return com.amp.entities.details.SpokenLanguage(
        englishName = englishName,
        iso6391 = iso6391,
        name = name
    )
}

private fun CollectionData.toDomain(): com.amp.entities.details.Collection {
    return com.amp.entities.details.Collection(
        id = id,
        name = name,
        posterPath = posterPath,
        backdropPath = backdropPath
    )
}
