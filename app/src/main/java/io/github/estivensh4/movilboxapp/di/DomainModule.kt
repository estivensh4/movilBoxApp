package io.github.estivensh4.movilboxapp.di

import io.github.estivensh4.movilboxapp.domain.repository.ProductsRepository
import io.github.estivensh4.movilboxapp.domain.useCase.DeleteLocalProductByIdUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.GetAllCategoriesUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.GetAllProductsUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.GetHistoryUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.GetSingleLocalProductUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.GetSingleProductUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.InsertHistoryUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.InsertLocalProductUseCase
import io.github.estivensh4.movilboxapp.domain.useCase.UseCases
import org.koin.dsl.module

val domainModule = module {
    single { provideUseCases(get()) }
}

fun provideUseCases(productsRepository: ProductsRepository): UseCases {
    return UseCases(
        getAllCategoriesUseCase = GetAllCategoriesUseCase(productsRepository),
        getAllProductsUseCase = GetAllProductsUseCase(productsRepository),
        getSingleProductUseCase = GetSingleProductUseCase(productsRepository),
        insertLocalProductUseCase = InsertLocalProductUseCase(productsRepository),
        deleteLocalProductByIdUseCase = DeleteLocalProductByIdUseCase(productsRepository),
        getSingleLocalProductUseCase = GetSingleLocalProductUseCase(productsRepository),
        insertHistoryUseCase = InsertHistoryUseCase(productsRepository),
        getHistoryUseCase = GetHistoryUseCase(productsRepository)
    )
}