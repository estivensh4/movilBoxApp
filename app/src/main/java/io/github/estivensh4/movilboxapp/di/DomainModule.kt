package io.github.estivensh4.movilboxapp.di

import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository
import io.github.estivensh4.movilboxapp.domain.useCase.AddProductUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.DeleteProductUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.GetAllCategoriesUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.GetAllProductsUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.GetSingleProductUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.UpdateProductUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.UseCases
import org.koin.dsl.module

val domainModule = module {
    single { provideUseCases(get()) }
}

fun provideUseCases(productsRepository: ProductsRepository): UseCases {
    return UseCases(
        addProductUseCase = AddProductUseCase(productsRepository),
        deleteProductUseCase = DeleteProductUseCase(productsRepository),
        getAllCategoriesUseCase = GetAllCategoriesUseCase(productsRepository),
        getAllProductsUseCase = GetAllProductsUseCase(productsRepository),
        updateProductUseCase = UpdateProductUseCase(productsRepository),
        getSingleProductUseCase = GetSingleProductUseCase(productsRepository)
    )
}