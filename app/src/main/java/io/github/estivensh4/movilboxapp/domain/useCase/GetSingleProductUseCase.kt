package io.github.estivensh4.movilboxapp.domain.useCase

import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository

class GetSingleProductUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(id: Int) = productsRepository.getSingleProduct(id)
}
