package io.github.estivensh4.movilboxapp.data.repository

import io.github.estivensh4.movilboxapp.data.local.dao.HistoryDao
import io.github.estivensh4.movilboxapp.data.local.dao.ProductsDao
import io.github.estivensh4.movilboxapp.data.mapper.toEntity
import io.github.estivensh4.movilboxapp.data.mapper.toHistory
import io.github.estivensh4.movilboxapp.domain.model.GetAllProductsOutput
import io.github.estivensh4.movilboxapp.domain.model.History
import io.github.estivensh4.movilboxapp.domain.model.Product
import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductsRepositoryImpl(
    private val httpClient: HttpClient,
    private val productsDao: ProductsDao,
    private val historyDao: HistoryDao
) : ProductsRepository {

    override suspend fun getAllProducts(): Result<GetAllProductsOutput> {
        return try {
            Result.success(httpClient.get(ENDPOINT_PRODUCTS).body())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun getAllCategories(): Result<List<String>> {
        return try {
            Result.success(httpClient.get("$ENDPOINT_PRODUCTS/categories").body())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun getSingleProduct(id: Int): Result<Product> {
        return try {
            Result.success(httpClient.get("$ENDPOINT_PRODUCTS/$id").body())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun insertLocalProduct(product: Product) {
        productsDao.insertSingleProduct(product.toEntity())
    }

    override suspend fun deleteLocalProductById(id: Int) {
        productsDao.deleteProductById(id)
    }

    override fun getSingleLocalProduct(id: Int): Flow<Product?> {
        return productsDao.getSingleProduct(id).map { it.toHistory() }
    }

    override fun getHistory(): Flow<History?> {
        return historyDao.getHistory().map { it.toHistory() }
    }

    override suspend fun insertHistory(history: History) {
        historyDao.insertHistory(history.toEntity())
    }

    companion object {
        const val ENDPOINT_PRODUCTS = "products"
    }

}