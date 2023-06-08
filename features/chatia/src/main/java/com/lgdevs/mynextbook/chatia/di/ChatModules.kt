package com.lgdevs.mynextbook.chatia.di

import com.lgdevs.mynextbook.chatia.data.local.di.ChatDataModule
import com.lgdevs.mynextbook.chatia.data.remote.di.ChatRemoteModule
import com.lgdevs.mynextbook.chatia.data.repository.di.ChatRepositoryModule
import com.lgdevs.mynextbook.chatia.domain.di.ChatDomainModule
import com.lgdevs.mynextbook.chatia.presentation.di.ChatPresentationModule

object ChatModules {
    val modules = listOf(
        ChatDataModule.modules,
        ChatRemoteModule.modules,
        ChatDomainModule.modules,
        ChatPresentationModule.modules,
        ChatRepositoryModule.modules,
    )
}
