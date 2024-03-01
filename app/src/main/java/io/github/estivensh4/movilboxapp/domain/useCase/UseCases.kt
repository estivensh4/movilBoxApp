package io.github.estivensh4.movilboxapp.domain.useCase

data class UseCases(
    val addProductUseCase: AddProductUseCase,
    val deleteProductUseCase: DeleteProductUseCase,
    val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    val getAllProductsUseCase: GetAllProductsUseCase,
    val updateProductUseCase: UpdateProductUseCase,
    val getSingleProductUseCase: GetSingleProductUseCase
)
