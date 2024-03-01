package io.github.estivensh4.movilboxapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.github.estivensh4.movilboxapp.domain.model.Router
import io.github.estivensh4.movilboxapp.presentation.screens.ProductsScreen
import io.github.estivensh4.movilboxapp.presentation.theme.MovilBoxAppTheme
import io.github.estivensh4.movilboxapp.util.route

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovilBoxAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Router.Products.route) {
                    route(Router.Products) { ProductsScreen(navController = navController) }
                }
            }
        }
    }
}