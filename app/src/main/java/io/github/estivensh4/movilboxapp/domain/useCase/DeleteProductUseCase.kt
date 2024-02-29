package io.github.estivensh4.movilboxapp.domain.useCase

import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository

class DeleteProductUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(id: Int) = productsRepository.deleteProduct(id)
}
