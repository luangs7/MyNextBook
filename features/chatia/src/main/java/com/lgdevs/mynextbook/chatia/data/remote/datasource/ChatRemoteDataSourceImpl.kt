package com.lgdevs.mynextbook.chatia.data.remote.datasource

import com.lgdevs.mynextbook.chatia.data.remote.model.ChatRequest
import com.lgdevs.mynextbook.chatia.data.remote.model.ChatRequestMessage
import com.lgdevs.mynextbook.chatia.data.remote.service.ChatService
import com.lgdevs.mynextbook.chatia.data.repository.datasource.ChatRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ChatRemoteDataSourceImpl(
    private val chatService: ChatService,
) : ChatRemoteDataSource {
    override fun sendQuestion(message: String): Flow<String> = flow {
        val result = chatService.getResponse(ChatRequest(messages = listOf(ChatRequestMessage(content = message))))
        emit(result.choices.first().text)
    }.flowOn(Dispatchers.IO)
}
