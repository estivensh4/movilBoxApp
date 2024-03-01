package io.github.estivensh4.movilboxapp.di

import io.github.estivensh4.movilboxapp.data.repository.ProductsRepositoryImpl
import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single { provideHttpClient() }
    single<ProductsRepository> { ProductsRepositoryImpl(get()) }
}

fun provideHttpClient(): HttpClient {
    return HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
        defaultRequest {
            url("https://dummyjson.com/")
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
        expectSuccess = true
    }
}