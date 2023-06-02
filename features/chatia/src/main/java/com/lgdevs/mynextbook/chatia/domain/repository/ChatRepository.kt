package com.lgdevs.mynextbook.chatia.domain.repository

import com.lgdevs.mynextbook.chatia.domain.model.Message
import com.lgdevs.mynextbook.common.base.ApiResult
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun sendQuestion(question: String): Flow<ApiResult<String>>
    fun getMessages(userId: String): Flow<ApiResult<List<Message>>>
    fun saveMessage(userId: String, message: Message): Flow<ApiResult<Unit>>
}
