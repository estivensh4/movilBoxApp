package io.github.estivensh4.movilboxapp.domain.useCase

import io.github.estivensh4.movilboxapp.domain.model.History
import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository

class InsertHistoryUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(history: History) = productsRepository.insertHistory(history)
}
