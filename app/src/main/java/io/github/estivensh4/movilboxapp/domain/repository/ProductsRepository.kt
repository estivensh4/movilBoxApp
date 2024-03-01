package io.github.estivensh4.movilboxapp.domain.repository

import io.github.estivensh4.movilboxapp.domain.model.GetAllProductsOutput
import io.github.estivensh4.movilboxapp.domain.model.Product

interface ProductsRepository {
    suspend fun getAllProducts(): Result<GetAllProductsOutput>
    suspend fun getAllCategories(): Result<List<String>>
    suspend fun addProduct(product: Product): Result<Product>
    suspend fun updateProduct(id: Int, product: Product): Result<Product>
    suspend fun deleteProduct(id: Int): Result<Product>
    suspend fun getSingleProduct(id: Int): Result<Product>
}