package com.lgdevs.mynextbook.favorites.di

import com.lgdevs.mynextbook.favorites.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectDynamicFeature() = loadFeatureModules

private val loadFeatureModules by lazy {
    loadKoinModules(favoritesModule)
}

val favoritesModule = module {
    viewModel { FavoritesViewModel(get(), get(), get()) }
}
