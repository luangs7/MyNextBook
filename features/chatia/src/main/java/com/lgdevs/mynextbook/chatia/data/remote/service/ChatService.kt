package com.lgdevs.mynextbook.chatia.data.remote.service

import com.lgdevs.mynextbook.chatia.data.remote.model.ChatRequest
import com.lgdevs.mynextbook.chatia.data.remote.model.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface ChatService {
    @POST
    suspend fun getResponse(
        @Body request: ChatRequest,
        @Url url: String = "https://api.openai.com/v1/completions",
        @Header("Authorization") apiKey: String = "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",
    ): ChatResponse
}
