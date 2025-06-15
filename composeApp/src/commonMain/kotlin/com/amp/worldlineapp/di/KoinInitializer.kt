package com.amp.worldlineapp.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

object KoinInitializer {

    fun initKoin(additionalModules: List<Module> = emptyList()) {
        startKoin {
            modules(appModules + additionalModules)
        }
    }

    fun stopKoin() {
        stopKoin()
    }
}