package com.amp.di

import com.amp.usecases.GetMovieDetailsUseCase
import com.amp.usecases.GetPopularMoviesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetPopularMoviesUseCase() }
    factory { GetMovieDetailsUseCase() }
}