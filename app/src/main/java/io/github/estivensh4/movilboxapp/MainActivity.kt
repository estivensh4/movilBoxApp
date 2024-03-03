package io.github.estivensh4.movilboxapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.estivensh4.movilboxapp.domain.model.Args
import io.github.estivensh4.movilboxapp.domain.model.Router
import io.github.estivensh4.movilboxapp.presentation.screens.ProductDetailScreen
import io.github.estivensh4.movilboxapp.presentation.screens.ProductsScreen
import io.github.estivensh4.movilboxapp.presentation.theme.MovilBoxAppTheme
import io.github.estivensh4.movilboxapp.util.route

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovilBoxAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Router.Products.route
                ) {
                    route(Router.Products) { ProductsScreen(navController = navController) }
                    route(
                        route = Router.ProductDetail,
                        arguments = listOf(
                            navArgument(Args.productId) { type = NavType.IntType }
                        )
                    ) {
                        val productId = it.arguments?.getInt(Args.productId) ?: 0
                        ProductDetailScreen(
                            navController = navController,
                            productId = productId
                        )
                    }
                }
            }
        }
    }
}