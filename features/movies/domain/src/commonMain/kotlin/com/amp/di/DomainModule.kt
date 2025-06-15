package com.amp.di

import com.amp.integration.GetMovieDetailsUseCase
import com.amp.integration.GetPopularMoviesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetPopularMoviesUseCase() }
    factory { GetMovieDetailsUseCase() }
}