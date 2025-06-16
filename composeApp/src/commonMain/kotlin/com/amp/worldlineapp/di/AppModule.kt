package com.amp.worldlineapp.di

import com.amp.di.dataModule
import com.amp.di.domainModule
import com.amp.network.di.networkModule
import com.amp.presentation.di.presentationModule
import org.koin.core.module.Module

val appModules: List<Module> = listOf(
    presentationModule,
    domainModule,
    dataModule,
    networkModule
)