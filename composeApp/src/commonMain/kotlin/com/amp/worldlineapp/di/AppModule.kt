package com.amp.worldlineapp.di

import com.amp.di.dataModule
import com.amp.di.domainModule
import com.amp.network.di.networkModule
import org.koin.core.module.Module

val appModules: List<Module> = listOf(
    domainModule,
    dataModule,
    networkModule
)