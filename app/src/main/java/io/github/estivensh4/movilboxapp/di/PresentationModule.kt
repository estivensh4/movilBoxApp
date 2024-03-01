package io.github.estivensh4.movilboxapp.di

import io.github.estivensh4.movilboxapp.presentation.viewmodels.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { ProductsViewModel(get()) }
}