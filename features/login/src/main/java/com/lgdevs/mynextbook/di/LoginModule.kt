package com.lgdevs.mynextbook.di

import com.lgdevs.mynextbook.login.CloudServicesHolder
import com.lgdevs.mynextbook.login.CloudServicesHolderImpl
import com.lgdevs.mynextbook.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel {
        LoginViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    factory<CloudServicesHolder> { CloudServicesHolderImpl(get(), get()) }
}
