package io.github.estivensh4.movilboxapp.util

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.github.estivensh4.movilboxapp.domain.model.Router

fun NavGraphBuilder.route(
    route: Router,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route.route,
        arguments = arguments,
        content = content
    )
}

fun NavHostController.navigate(route: Router) {
    navigate(route.route) {
        popUpTo(route.route) {
            inclusive = true
        }
    }
}