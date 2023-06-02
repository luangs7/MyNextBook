package com.lgdevs.mynextbook.chatia.data.repository.datasource

import com.lgdevs.mynextbook.chatia.data.repository.model.MessageRepo
import kotlinx.coroutines.flow.Flow

interface ChatLocalDataSource {
    fun getMessages(userId: String): Flow<List<MessageRepo>>
    fun saveMessage(userId: String, message: MessageRepo): Flow<Unit>
}
