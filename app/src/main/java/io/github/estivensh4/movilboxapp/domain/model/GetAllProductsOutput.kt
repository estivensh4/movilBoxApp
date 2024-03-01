package io.github.estivensh4.movilboxapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GetAllProductsOutput(
    val products: List<Product> = emptyList(),
    val total: Int = 0,
    val skip: Int = 0,
    val limit: Int = 0
)

@Serializable
data class Product(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: Int = 0,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val brand: String = "",
    val category: String = "",
    val thumbnail: String = "",
    val images: List<String> = emptyList()
)