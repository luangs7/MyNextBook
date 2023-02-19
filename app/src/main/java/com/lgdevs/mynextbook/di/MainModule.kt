package com.lgdevs.mynextbook.di

import com.lgdevs.mynextbook.main.MainViewModel
import com.lgdevs.mynextbook.observer.KObserver
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val LOGOUT_OBSERVER_QUALIFIER = named("logoutObserver")

val mainModule = module {
    single(LOGOUT_OBSERVER_QUALIFIER) { KObserver<Unit>() }
    viewModel {  MainViewModel(get(), get(LOGOUT_OBSERVER_QUALIFIER)) }
}