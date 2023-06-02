package com.lgdevs.mynextbook.chatia.data.remote.model

data class ChatResponse(
    val choices: List<ChatChoice>,
)

data class ChatChoice(
    val text: String,
    val index: Int,
    val logprobs: Any?,
    val finishReason: String?,
)
