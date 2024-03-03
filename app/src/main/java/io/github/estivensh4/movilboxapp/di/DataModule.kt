package io.github.estivensh4.movilboxapp.di

import android.content.Context
import androidx.room.Room
import io.github.estivensh4.movilboxapp.data.local.DATABASE_PRODUCTS
import io.github.estivensh4.movilboxapp.data.local.MovilBoxDatabase
import io.github.estivensh4.movilboxapp.data.local.dao.HistoryDao
import io.github.estivensh4.movilboxapp.data.local.dao.ProductsDao
import io.github.estivensh4.movilboxapp.data.repository.ProductsRepositoryImpl
import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { provideHttpClient() }
    single<ProductsRepository> { ProductsRepositoryImpl(get(), get(), get()) }
    single { provideMovilBoxDatabase(androidContext()) }
    single { provideHistoryDao(get()) }
    single { provideProductsDao(get()) }
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

fun provideMovilBoxDatabase(context: Context): MovilBoxDatabase {
    return Room.databaseBuilder(context, MovilBoxDatabase::class.java, DATABASE_PRODUCTS)
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
}

fun provideProductsDao(movilBoxDatabase: MovilBoxDatabase): ProductsDao =
    movilBoxDatabase.productsDao

fun provideHistoryDao(movilBoxDatabase: MovilBoxDatabase): HistoryDao = movilBoxDatabase.historyDao