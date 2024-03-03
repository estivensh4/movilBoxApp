package io.github.estivensh4.movilboxapp.domain.useCase

import io.github.estivensh4.movilboxapp.domain.model.Product
import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository

class InsertLocalProductUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(product: Product) = productsRepository.insertLocalProduct(product)
}
