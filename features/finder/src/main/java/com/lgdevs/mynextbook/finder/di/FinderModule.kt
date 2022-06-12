package com.lgdevs.mynextbook.finder.di

import com.lgdevs.mynextbook.finder.preview.viewmodel.PreviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val finderModule = module {
    viewModel { PreviewViewModel(get(), get(), get()) }
}