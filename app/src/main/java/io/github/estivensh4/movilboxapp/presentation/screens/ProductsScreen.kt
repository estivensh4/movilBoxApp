package io.github.estivensh4.movilboxapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.estivensh4.movilboxapp.presentation.components.CustomSearchBar
import io.github.estivensh4.movilboxapp.presentation.components.ProductsList
import io.github.estivensh4.movilboxapp.presentation.theme.MovilBoxAppTheme
import io.github.estivensh4.movilboxapp.presentation.viewmodels.ProductsViewModel
import io.github.estivensh4.movilboxapp.util.DataSource
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductsScreen(
    navController: NavController,
    productsViewModel: ProductsViewModel = koinViewModel()
) {
    var searchProduct by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchHistory = remember { mutableStateListOf("") }
    var selectedCategory by rememberSaveable { mutableIntStateOf(0) }
    val searchResults by productsViewModel.searchResults.collectAsStateWithLifecycle()
    val productsState by productsViewModel.productsState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(productsState) {
        when (productsState.error.isNotEmpty()) {
            true -> scope.launch { snackbarHostState.showSnackbar(productsState.error) }
            false -> {}
        }
    }

    Scaffold(
        topBar = {
            CustomSearchBar(
                searchProduct = productsViewModel.searchQuery,
                onSearchProductChange = productsViewModel::onSearchQueryChange,
                onActiveChange = { active = it },
                searchHistory = searchHistory,
                active = active,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            item {
                Spacer(modifier = Modifier.size(36.dp))
                Text(
                    text = "Order online\ncollect in store",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.size(36.dp))
            }
            item {
                ScrollableTabRow(
                    selectedTabIndex = selectedCategory
                ) {
                    DataSource.categoriesList.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = selectedCategory == index,
                            onClick = { selectedCategory = index },
                        )
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
            }
            item {
                when {
                    productsState.isLoading -> CircularProgressIndicator()
                    productsState.error.isNotEmpty() -> {
                        Text(text = productsState.error)
                    }
                    else -> {
                        ProductsList(
                            navController = navController,
                            /*filteredProducts = productsState.productsList.sortedByDescending { it.rating }
                                .filter { it.category == productsState.categoriesList[selectedCategory] }*/
                            filteredProducts = productsState.productsList
                        )
                    }
                }
                Spacer(modifier = Modifier.size(40.dp))
            }
        }
    }

}

@Preview
@Composable
fun ProductsScreenPrev() {
    MovilBoxAppTheme {
        ProductsScreen(rememberNavController())
    }
}