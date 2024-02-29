package io.github.estivensh4.movilboxapp.domain.useCase

import io.github.estivensh4.movilboxapp.domain.model.Product
import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository

class UpdateProductUseCase(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(id: Int, product: Product) =
        productsRepository.updateProduct(id, product)
}
