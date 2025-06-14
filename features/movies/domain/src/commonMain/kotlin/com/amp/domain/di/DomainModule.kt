package com.amp.domain.di

import com.amp.domain.usecases.GetMovieDetailsUseCase
import com.amp.domain.usecases.GetPopularMoviesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetPopularMoviesUseCase() }
    factory { GetMovieDetailsUseCase() }
}