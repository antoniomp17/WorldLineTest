package com.amp.presentation.di

import com.amp.presentation.viewmodels.MoviesListViewModel
import com.amp.presentation.viewmodels.MovieDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {

    viewModelOf(::MoviesListViewModel)
    viewModelOf(::MovieDetailsViewModel)
}