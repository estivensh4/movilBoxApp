package io.github.estivensh4.movilboxapp.di

import io.github.estivensh4.movilboxapp.data.repository.ProductsRepositoryImpl
import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import org.koin.dsl.module

val dataModule = module {
    single { provideHttpClient() }
    single<ProductsRepository> { ProductsRepositoryImpl(get()) }
}

fun provideHttpClient(): HttpClient {
    return HttpClient(CIO) {
        defaultRequest {
            url("https://dummyjson.com/")
        }
    }
}