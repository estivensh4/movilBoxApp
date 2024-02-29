package io.github.estivensh4.movilboxapp

import android.app.Application
import io.github.estivensh4.movilboxapp.di.dataModule
import io.github.estivensh4.movilboxapp.di.domainModule
import io.github.estivensh4.movilboxapp.di.presentationModule
import org.koin.core.context.startKoin

class MovilBoxApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}

val appModule = listOf(
    dataModule,
    domainModule,
    presentationModule
)