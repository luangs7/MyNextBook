package com.lgdevs.mynextbook.chatia.data.remote.model

data class ChatRequest(
    val prompt: String,
    val temperature: Double = 0.5,
    val maxTokens: Int = 10,
    val stop: List<String> = listOf("\n"),
)
