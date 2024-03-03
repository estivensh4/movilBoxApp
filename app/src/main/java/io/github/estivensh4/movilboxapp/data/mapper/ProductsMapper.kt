package io.github.estivensh4.movilboxapp.data.mapper

import io.github.estivensh4.movilboxapp.data.local.entity.ProductEntity
import io.github.estivensh4.movilboxapp.domain.model.Product

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        description = description,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock,
        brand = brand,
        category = category,
        thumbnail = thumbnail,
        images = images
    )
}

fun ProductEntity?.toHistory(): Product? {
    return if (this != null){
        Product(
            id = id,
            title = title,
            description = description,
            price = price,
            discountPercentage = discountPercentage,
            rating = rating,
            stock = stock,
            brand = brand,
            category = category,
            thumbnail = thumbnail,
            images = images
        )
    } else null
}