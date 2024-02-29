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
        return httpClient.get(ENDPOINT_PRODUCTS).body()
    }

    override suspend fun getAllCategories(): Result<List<String>> {
        return httpClient.get("$ENDPOINT_PRODUCTS/categories").body()
    }

    override suspend fun addProduct(product: Product): Result<Product> {
        return httpClient.post("$ENDPOINT_PRODUCTS/add") {
            setBody(product)
        }.body()
    }

    override suspend fun updateProduct(id: Int, product: Product): Result<Product> {
        return httpClient.put("$ENDPOINT_PRODUCTS/$id") {
            setBody(product)
        }.body()
    }

    override suspend fun deleteProduct(id: Int): Result<Product> {
        return httpClient.delete("$ENDPOINT_PRODUCTS/$id").body()
    }

    companion object {
        const val ENDPOINT_PRODUCTS = "products"
    }

}