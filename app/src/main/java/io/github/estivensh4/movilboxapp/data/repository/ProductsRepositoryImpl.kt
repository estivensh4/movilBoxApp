package io.github.estivensh4.movilboxapp.data.repository

import io.github.estivensh4.movilboxapp.domain.model.GetAllProductsOutput
import io.github.estivensh4.movilboxapp.domain.model.Product
import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class ProductsRepositoryImpl(
    private val httpClient: HttpClient
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

    override suspend fun addProduct(product: Product): Result<Product> {
        return try {
            Result.success(httpClient.post("$ENDPOINT_PRODUCTS/add") {
                setBody(product)
            }.body())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun updateProduct(id: Int, product: Product): Result<Product> {
        return try {
            Result.success(httpClient.put("$ENDPOINT_PRODUCTS/$id") {
                setBody(product)
            }.body())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun deleteProduct(id: Int): Result<Product> {
        return try {
            Result.success(httpClient.delete("$ENDPOINT_PRODUCTS/$id").body())
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    companion object {
        const val ENDPOINT_PRODUCTS = "products"
    }

}