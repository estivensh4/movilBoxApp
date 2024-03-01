package io.github.estivensh4.movilboxapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.estivensh4.movilboxapp.domain.model.Product
import io.github.estivensh4.movilboxapp.domain.model.Router
import io.github.estivensh4.movilboxapp.util.DataSource

@Composable
fun ProductsList(
    state: LazyListState,
    navController: NavController,
    filteredProducts: List<Product>,
    modifier: Modifier = Modifier
) {
    if (filteredProducts.isNotEmpty()) {
        LazyRow(
            state = state,
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = filteredProducts,
                key = { it.id }
            ) { product ->
                ItemProduct(product = product) {
                    navController.navigate(Router.ProductDetail.createRoute(product.id))
                }
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(
                text = "there are no products for this category",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        }
    }
}