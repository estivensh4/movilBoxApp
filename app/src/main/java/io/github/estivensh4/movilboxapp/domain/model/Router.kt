package io.github.estivensh4.movilboxapp.domain.model

sealed class Router(val route: String) {
    data object Products : Router("products")
    data object ProductDetail : Router("product_detail/{${Args.productId}}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
}

object Args {
    const val productId = "productId"
}