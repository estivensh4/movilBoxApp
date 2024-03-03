package io.github.estivensh4.movilboxapp.domain.useCase

import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository

class GetSingleLocalProductUseCase(
    private val productsRepository: ProductsRepository
) {
    operator fun invoke(id: Int) = productsRepository.getSingleLocalProduct(id)
}
