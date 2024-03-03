package io.github.estivensh4.movilboxapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.estivensh4.movilboxapp.domain.model.History
import io.github.estivensh4.movilboxapp.domain.model.Product
import io.github.estivensh4.movilboxapp.domain.useCase.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val useCases: UseCases
) : ViewModel() {

    private var _productsState = MutableStateFlow(ProductsState())
    val productsState = _productsState.asStateFlow()

    init {
        getAllCategories()
        getAllProducts()
        getHistory()
    }

    fun onEvent(event: ProductsEvents) {
        when (event) {
            is ProductsEvents.InsertHistory -> insertHistory(event.history)
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            _productsState.value = _productsState.value.copy(isLoading = true)
            useCases.getAllCategoriesUseCase()
                .onSuccess { categories ->
                    _productsState.value = _productsState.value.copy(
                        isLoading = false,
                        categoriesList = categories
                    )
                }.onFailure { error ->
                    _productsState.value = _productsState.value.copy(
                        isLoading = false,
                        error = error.message.orEmpty()
                    )
                }
        }
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _productsState.value = _productsState.value.copy(isLoading = true)
            useCases.getAllProductsUseCase()
                .onSuccess { getAllProductsOutput ->
                    _productsState.value = _productsState.value.copy(
                        isLoading = false,
                        productsList = getAllProductsOutput.products
                    )
                }.onFailure { error ->
                    _productsState.value = _productsState.value.copy(
                        isLoading = false,
                        error = error.message.orEmpty()
                    )
                }
        }
    }

    private fun insertHistory(history: History) {
        viewModelScope.launch {
            useCases.insertHistoryUseCase(history)
        }
    }

    private fun getHistory() {
        useCases.getHistoryUseCase()
            .onEach {
                _productsState.value = _productsState.value.copy(history = it)
            }.launchIn(viewModelScope)
    }
}

data class ProductsState(
    val categoriesList: List<String> = emptyList(),
    val productsList: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val history: History? = null
)

sealed class ProductsEvents {
    data class InsertHistory(val history: History) : ProductsEvents()
}