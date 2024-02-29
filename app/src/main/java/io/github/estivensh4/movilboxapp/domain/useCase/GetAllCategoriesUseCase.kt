package io.github.estivensh4.movilboxapp.domain.useCase

import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository

class GetAllCategoriesUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke() = productsRepository.getAllCategories()
}
