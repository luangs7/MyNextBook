package com.lgdevs.mynextbook.chatia.data.remote.model

data class ChatRequest(
    var model: String = "gpt-3.5-turbo",
    val temperature: Double = 1.0,
    val maxTokens: Int = 50,
    var messages: List<ChatRequestMessage>,
)

data class ChatRequestMessage(
    val role: String = "user",
    val content: String,
)
