package com.lgdevs.mynextbook.chatia.domain.di

import com.lgdevs.mynextbook.chatia.domain.interaction.GetMessagesUseCase
import com.lgdevs.mynextbook.chatia.domain.interaction.SaveMessageUseCase
import com.lgdevs.mynextbook.chatia.domain.interaction.SendQuestionUseCase
import com.lgdevs.mynextbook.chatia.domain.repository.ChatRepository
import org.koin.dsl.module

object ChatDomainModule {
    val modules = module {
        factory { GetMessagesUseCase(get<ChatRepository>()::getMessages) }
        factory { SaveMessageUseCase(get<ChatRepository>()::saveMessage) }
        factory { SendQuestionUseCase(get<ChatRepository>()::sendQuestion) }
    }
}