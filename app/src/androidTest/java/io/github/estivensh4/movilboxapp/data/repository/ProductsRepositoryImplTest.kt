package io.github.estivensh4.movilboxapp.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import io.github.estivensh4.movilboxapp.data.local.MovilBoxDatabase
import io.github.estivensh4.movilboxapp.data.local.dao.HistoryDao
import io.github.estivensh4.movilboxapp.data.local.dao.ProductsDao
import io.github.estivensh4.movilboxapp.domain.model.GetAllProductsOutput
import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository
import io.github.estivensh4.movilboxapp.util.DataSource
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class RepositoryImplTest : KoinTest {

    private val productsRepository: ProductsRepository by inject()
    private lateinit var productsDao: ProductsDao
    private lateinit var historyDao: HistoryDao
    private lateinit var db: MovilBoxDatabase

    @Before
    fun before() {
        stopKoin()
    }


    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun getAllProducts() = testApplication {

        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(
                module {
                    single {
                        createClient {
                            this.install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                                json()
                            }
                            defaultRequest {
                                url(BASE_URL)
                            }
                        }
                    }
                    single<ProductsRepository> {
                        val context = ApplicationProvider.getApplicationContext<Context>()
                        //val context = InstrumentationRegistry.getInstrumentation().context
                        db = Room.inMemoryDatabaseBuilder(
                            context = context,
                            klass = MovilBoxDatabase::class.java
                        ).build()
                        productsDao = db.productsDao
                        historyDao = db.historyDao
                        ProductsRepositoryImpl(
                            httpClient = get(),
                            productsDao = productsDao,
                            historyDao = historyDao
                        )
                    }
                }
            )
        }

        externalServices {
            hosts(BASE_URL) {
                install(ContentNegotiation) {
                    json()
                }
                routing {
                    get(ProductsRepositoryImpl.ENDPOINT_PRODUCTS) {
                        call.respond(
                            GetAllProductsOutput(
                                products = listOf(
                                    DataSource.product
                                ),
                                total = 1
                            )
                        )
                    }
                }
            }
        }
        val response = productsRepository.getAllProducts()
        assertThat(response.isSuccess).isTrue()
    }

    @Test
    fun getAllProductsReturnFailure() = testApplication {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(
                module {
                    single {
                        createClient {
                            this.install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                                json()
                            }
                            defaultRequest {
                                url(BASE_URL)
                            }
                        }
                    }
                    single<ProductsRepository> {
                        val context = ApplicationProvider.getApplicationContext<Context>()
                        db = Room.inMemoryDatabaseBuilder(
                            context = context,
                            klass = MovilBoxDatabase::class.java
                        ).build()
                        productsDao = db.productsDao
                        historyDao = db.historyDao
                        ProductsRepositoryImpl(
                            httpClient = get(),
                            productsDao = productsDao,
                            historyDao = historyDao
                        )
                    }
                }
            )
        }

        externalServices {
            hosts(BASE_URL) {
                install(ContentNegotiation) {
                    json()
                }
                routing {
                    get("${ProductsRepositoryImpl.ENDPOINT_PRODUCTS}1") {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            GetAllProductsOutput(
                                products = listOf(
                                    DataSource.product
                                ),
                                total = 1
                            ),
                        )
                    }
                }
            }
        }
        val response = productsRepository.getAllProducts()
        assertThat(response.isFailure).isTrue()
    }

    companion object {
        const val BASE_URL = "http://localhost:80"
    }
}