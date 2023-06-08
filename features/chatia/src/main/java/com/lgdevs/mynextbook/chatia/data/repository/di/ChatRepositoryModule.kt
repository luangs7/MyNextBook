package com.lgdevs.mynextbook.chatia.data.repository.di

import com.lgdevs.mynextbook.chatia.data.repository.implementation.ChatRepositoryImpl
import com.lgdevs.mynextbook.chatia.data.repository.mapper.MessageRepoMapper
import com.lgdevs.mynextbook.chatia.domain.repository.ChatRepository
import org.koin.dsl.module

object ChatRepositoryModule {
    val modules = module {
        single { MessageRepoMapper() }
        factory<ChatRepository> { ChatRepositoryImpl(get(), get(), get()) }
    }
}