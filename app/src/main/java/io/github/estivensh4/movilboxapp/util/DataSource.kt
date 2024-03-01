package io.github.estivensh4.movilboxapp.util

import io.github.estivensh4.movilboxapp.domain.model.Product

object DataSource {
    val product = Product(
        id = 1,
        title = "Huawei P30",
        description = "Huawei’s re-badged P30 Pro New Edition was officially unveiled yesterday in Germany and now the device has made its way to the UK.",
        price = 499,
        discountPercentage = 10.58,
        rating = 4.09,
        stock = 32,
        brand = "Huawei",
        category = "smartphones",
        thumbnail = "https://cdn.dummyjson.com/product-images/5/thumbnail.jpg",
        images = listOf(
            "https://cdn.dummyjson.com/product-images/5/1.jpg",
            "https://cdn.dummyjson.com/product-images/5/2.jpg",
            "https://cdn.dummyjson.com/product-images/5/3.jpg"
        )
    )

}