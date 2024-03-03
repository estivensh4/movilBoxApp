package io.github.estivensh4.movilboxapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import io.github.estivensh4.movilboxapp.domain.model.History
import io.github.estivensh4.movilboxapp.domain.model.Product
import io.github.estivensh4.movilboxapp.presentation.components.CustomSearchBar
import io.github.estivensh4.movilboxapp.presentation.components.ProductsList
import io.github.estivensh4.movilboxapp.presentation.theme.MovilBoxAppTheme
import io.github.estivensh4.movilboxapp.presentation.viewmodels.ProductsEvents
import io.github.estivensh4.movilboxapp.presentation.viewmodels.ProductsViewModel
import io.github.estivensh4.movilboxapp.util.FilterList
import io.github.estivensh4.movilboxapp.util.capitalize
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductsScreen(
    navController: NavController,
    productsViewModel: ProductsViewModel = koinViewModel()
) {
    var searchProduct by remember { mutableStateOf("") }
    var filterValue by rememberSaveable { mutableStateOf(FilterList.RATING.name) }
    var active by remember { mutableStateOf(false) }
    val searchHistory = remember { mutableStateListOf("") }
    var selectedCategory by rememberSaveable { mutableIntStateOf(0) }
    val productsState by productsViewModel.productsState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(productsState) {
        when (productsState.error.isNotEmpty()) {
            true -> scope.launch { snackbarHostState.showSnackbar(productsState.error) }
            false -> {}
        }
    }

    LaunchedEffect(productsState) {
        productsState.history?.let {
            searchHistory.clear()
            searchHistory.addAll(it.historyList)
        }
    }

    Scaffold(
        topBar = {
            CustomSearchBar(
                searchProduct = searchProduct,
                onSearchProductChange = { searchProduct = it },
                onActiveChange = { active = it },
                searchHistory = searchHistory,
                active = active,
                modifier = Modifier.padding(horizontal = 16.dp),
                onClickFilter = {
                    filterValue = it
                    scope.launch { lazyListState.animateScrollToItem(0) }
                },
                onSearch = {
                    active = false
                    searchHistory.add(it)
                    productsViewModel.onEvent(
                        ProductsEvents.InsertHistory(
                            History(
                                historyList = searchHistory
                            )
                        )
                    )
                }
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
            horizontalAlignment = Alignment.CenterHorizontally
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
                when {
                    productsState.categoriesList.isNotEmpty() -> {
                        ScrollableTabRow(
                            selectedTabIndex = selectedCategory
                        ) {
                            productsState.categoriesList.forEachIndexed { index, title ->
                                Tab(
                                    text = {
                                        Text(
                                            title.capitalize(),
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    selected = selectedCategory == index,
                                    onClick = { selectedCategory = index },
                                )
                            }
                        }
                        Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }
            item {
                when {
                    productsState.isLoading -> CircularProgressIndicator()
                    else -> {
                        ProductsList(
                            state = lazyListState,
                            navController = navController,
                            filteredProducts = productsState.productsList.filterProduct(
                                selectedCategory = productsState.categoriesList[selectedCategory],
                                filterValue = filterValue,
                                searchProduct = searchProduct
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.size(40.dp))
            }
        }
    }

}

fun List<Product>.filterProduct(
    selectedCategory: String,
    filterValue: String,
    searchProduct: String
): List<Product> {
    val categoryFiltered = filter { it.category == selectedCategory }
    val titleFiltered = filter { it.title.contains(searchProduct, ignoreCase = true) }
    return when (FilterList.getValue(filterValue)) {
        FilterList.PRICE -> {
            val priceFiltered = categoryFiltered.sortedByDescending { it.price }
            priceFiltered.filter { it in titleFiltered }
        }

        FilterList.DISCOUNT -> {
            val priceFiltered = categoryFiltered.sortedByDescending { it.discountPercentage }
            priceFiltered.filter { it in titleFiltered }
        }

        FilterList.CATEGORY -> {
            val priceFiltered = categoryFiltered.sortedByDescending { it.category }
            priceFiltered.filter { it in titleFiltered }
        }

        FilterList.RATING -> {
            val priceFiltered = categoryFiltered.sortedByDescending { it.rating }
            priceFiltered.filter { it in titleFiltered }
        }

        FilterList.STOCK -> {
            val priceFiltered = categoryFiltered.sortedByDescending { it.stock }
            priceFiltered.filter { it in titleFiltered }
        }

        FilterList.BRAND -> {
            val priceFiltered = categoryFiltered.sortedByDescending { it.brand }
            priceFiltered.filter { it in titleFiltered }
        }

        else -> categoryFiltered.filter { it in titleFiltered }
    }
}


@Preview
@Composable
fun ProductsScreenPrev() {
    MovilBoxAppTheme {
        ProductsScreen(rememberNavController())
    }
}