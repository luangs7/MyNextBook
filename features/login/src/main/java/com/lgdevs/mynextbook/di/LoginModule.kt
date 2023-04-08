package com.lgdevs.mynextbook.di

import com.lgdevs.mynextbook.login.analytics.LoginAnalytics
import com.lgdevs.mynextbook.login.analytics.LoginAnalyticsImpl
import com.lgdevs.mynextbook.login.holder.cloudservices.CloudServicesHolder
import com.lgdevs.mynextbook.login.holder.cloudservices.CloudServicesHolderImpl
import com.lgdevs.mynextbook.login.holder.usecase.LoginInteractorHolder
import com.lgdevs.mynextbook.login.holder.usecase.LoginInteractorHolderImpl
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
        )
    }
    factory<CloudServicesHolder> { CloudServicesHolderImpl(get(), get()) }
    factory<LoginInteractorHolder> {
        LoginInteractorHolderImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    factory<LoginAnalytics> { LoginAnalyticsImpl(get()) }
}
