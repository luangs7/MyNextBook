package com.lgdevs.mynextbook.chatia.data.local.datasource

import com.lgdevs.mynextbook.chatia.data.local.dao.ChatDao
import com.lgdevs.mynextbook.chatia.data.local.mapper.MessageEntityMapper
import com.lgdevs.mynextbook.chatia.data.repository.datasource.ChatLocalDataSource
import com.lgdevs.mynextbook.chatia.data.repository.model.MessageRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class ChatLocalDataSourceImpl(
    private val dao: ChatDao,
    private val mapper: MessageEntityMapper,
) : ChatLocalDataSource {
    override fun getMessages(userId: String): Flow<List<MessageRepo>> = flow {
        emit(dao.query(userId).map { mapper.toRepo(it) }.filter { it.message.isNotEmpty() }.asReversed())
    }.flowOn(Dispatchers.IO)

    override fun saveMessage(userId: String, message: MessageRepo): Flow<Unit> = flow {
        emit(dao.insert(mapper.toEntity(message)))
    }.flowOn(Dispatchers.IO)
}
