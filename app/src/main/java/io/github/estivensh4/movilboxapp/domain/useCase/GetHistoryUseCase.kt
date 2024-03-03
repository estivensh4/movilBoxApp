package io.github.estivensh4.movilboxapp.domain.useCase

import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository

class GetHistoryUseCase(
    private val productsRepository: ProductsRepository
) {
    operator fun invoke() = productsRepository.getHistory()
}
