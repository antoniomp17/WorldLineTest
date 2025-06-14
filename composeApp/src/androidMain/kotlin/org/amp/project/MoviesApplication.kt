package org.amp.project

import android.app.Application
import org.amp.worldlineapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Configuración específica de Android
            androidLogger(Level.DEBUG)
            androidContext(this@MoviesApplication)
            modules(appModules)
        }
    }
}
