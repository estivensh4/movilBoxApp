package io.github.estivensh4.movilboxapp.domain.useCase

import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository

class GetAllProductsUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke() = productsRepository.getAllProducts()
}
