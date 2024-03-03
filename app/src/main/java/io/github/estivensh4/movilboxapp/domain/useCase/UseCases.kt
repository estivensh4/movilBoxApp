package io.github.estivensh4.movilboxapp.domain.useCase

data class UseCases(
    val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    val getAllProductsUseCase: GetAllProductsUseCase,
    val getSingleProductUseCase: GetSingleProductUseCase,
    val insertLocalProductUseCase: InsertLocalProductUseCase,
    val deleteLocalProductByIdUseCase: DeleteLocalProductByIdUseCase,
    val getSingleLocalProductUseCase: GetSingleLocalProductUseCase,
    val getHistoryUseCase: GetHistoryUseCase,
    val insertHistoryUseCase: InsertHistoryUseCase
)
