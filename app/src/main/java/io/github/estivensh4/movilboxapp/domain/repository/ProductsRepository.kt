package io.github.estivensh4.movilboxapp.domain.repository

import io.github.estivensh4.movilboxapp.domain.model.GetAllProductsOutput
import io.github.estivensh4.movilboxapp.domain.model.History
import io.github.estivensh4.movilboxapp.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getAllProducts(): Result<GetAllProductsOutput>
    suspend fun getAllCategories(): Result<List<String>>
    suspend fun addProduct(product: Product): Result<Product>
    suspend fun updateProduct(id: Int, product: Product): Result<Product>
    suspend fun deleteProduct(id: Int): Result<Product>
    suspend fun getSingleProduct(id: Int): Result<Product>
    suspend fun insertLocalProduct(product: Product)
    suspend fun deleteLocalProductById(id: Int)
    fun getSingleLocalProduct(id: Int): Flow<Product?>
    fun getHistory(): Flow<History?>
    suspend fun insertHistory(history: History)
}