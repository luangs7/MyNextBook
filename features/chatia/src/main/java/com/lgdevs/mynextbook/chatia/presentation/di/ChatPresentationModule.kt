package com.lgdevs.mynextbook.chatia.presentation.di

import com.lgdevs.mynextbook.chatia.presentation.ChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ChatPresentationModule {
    val modules = module {
        viewModel { ChatViewModel(get(), get(), get(), get()) }
    }
}