package com.amp.di

import com.amp.datasources.Api
import com.amp.datasources.MoviesDataSource
import com.amp.repositories.MoviesRepositoryImpl
import com.amp.repository.MoviesRepository
import org.koin.dsl.module

val dataModule = module {

    factory { Api(httpClient = get()) }
    factory { MoviesDataSource(api = get()) }

    single<MoviesRepository> {
        MoviesRepositoryImpl(
            moviesDataSource = get()
        )
    }

}