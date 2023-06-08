package com.lgdevs.mynextbook.chatia.data.remote.service

import com.lgdevs.mynextbook.chatia.data.remote.model.ChatRequest
import com.lgdevs.mynextbook.chatia.data.remote.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface ChatService {
    @POST
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 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",
    )
    suspend fun getResponse(
        @Body request: ChatRequest,
        @Url url: String = "https://api.openai.com/v1/chat/completions",
    ): ChatResponse
}
