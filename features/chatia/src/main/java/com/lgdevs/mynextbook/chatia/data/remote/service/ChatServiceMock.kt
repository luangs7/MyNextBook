package com.lgdevs.mynextbook.chatia.data.remote.service

import com.lgdevs.mynextbook.chatia.data.remote.model.ChatChoice
import com.lgdevs.mynextbook.chatia.data.remote.model.ChatRequest
import com.lgdevs.mynextbook.chatia.data.remote.model.ChatResponse
import kotlinx.coroutines.delay
import retrofit2.http.Body
import retrofit2.http.Url

class ChatServiceMock : ChatService {
    override suspend fun getResponse(@Body request: ChatRequest, @Url url: String): ChatResponse {
        delay(150)
        return ChatResponse(listOf(ChatChoice("Hello my friend", 0, null, null)))
    }
}
