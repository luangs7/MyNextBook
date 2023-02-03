package com.lgdevs.mynextbook.login

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel {
        LoginViewModel(get(), get(), get(), get())
    }
}