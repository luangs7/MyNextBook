package com.lgdevs.mynextbook.filter.di

import com.lgdevs.mynextbook.filter.viewmodel.PreferencesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val preferencesModule = module {

    viewModel { PreferencesViewModel(get(), get()) }
}