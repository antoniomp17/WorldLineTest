package org.amp.worldlineapp.di

import com.amp.domain.di.domainModule
import org.koin.core.module.Module

val appModules: List<Module> = listOf(
    domainModule
)