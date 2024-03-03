package io.github.estivensh4.movilboxapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.estivensh4.movilboxapp.domain.model.Product
import io.github.estivensh4.movilboxapp.domain.useCase.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val useCases: UseCases
) : ViewModel() {

    private var _productDetailState = MutableStateFlow(ProductDetailState())
    val productDetailState = _productDetailState.asStateFlow()

    fun onEvent(event: ProductDetailEvent) {
        when (event) {
            is ProductDetailEvent.GetSingleProduct -> getSingleProduct(event.id)
            is ProductDetailEvent.ToggleLocalProduct -> toggleLocalProduct(
                event.favorite,
                event.product
            )

            is ProductDetailEvent.GetSingleLocalProduct -> getSingleLocalProduct(event.id)
        }
    }

    private fun getSingleProduct(id: Int) {
        viewModelScope.launch {
            _productDetailState.value = _productDetailState.value.copy(isLoading = true)
            useCases.getSingleProductUseCase(id)
                .onSuccess { product ->
                    _productDetailState.value = _productDetailState.value.copy(
                        isLoading = false,
                        product = product
                    )
                }.onFailure { error ->
                    _productDetailState.value = _productDetailState.value.copy(
                        isLoading = false,
                        error = error.message.orEmpty()
                    )
                }
        }
    }

    private fun toggleLocalProduct(
        favorite: Boolean,
        product: Product
    ) {
        viewModelScope.launch {
            if (favorite) {
                useCases.insertLocalProductUseCase(product)
            } else {
                useCases.deleteLocalProductByIdUseCase(product.id)
            }
        }
    }

    private fun getSingleLocalProduct(id: Int) {
        useCases.getSingleLocalProductUseCase(id)
            .onEach {
                _productDetailState.value = _productDetailState.value.copy(
                    localProduct = it
                )
            }.launchIn(viewModelScope)
    }

}

data class ProductDetailState(
    val product: Product? = null,
    val localProduct: Product? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)

sealed class ProductDetailEvent {
    data class GetSingleProduct(val id: Int) : ProductDetailEvent()
    data class GetSingleLocalProduct(val id: Int) : ProductDetailEvent()
    data class ToggleLocalProduct(val favorite: Boolean, val product: Product) :
        ProductDetailEvent()
}