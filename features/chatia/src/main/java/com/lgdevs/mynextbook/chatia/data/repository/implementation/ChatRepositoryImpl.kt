package com.lgdevs.mynextbook.chatia.data.repository.implementation

import com.lgdevs.mynextbook.chatia.data.repository.datasource.ChatLocalDataSource
import com.lgdevs.mynextbook.chatia.data.repository.datasource.ChatRemoteDataSource
import com.lgdevs.mynextbook.chatia.data.repository.mapper.MessageRepoMapper
import com.lgdevs.mynextbook.chatia.domain.model.Message
import com.lgdevs.mynextbook.chatia.domain.repository.ChatRepository
import com.lgdevs.mynextbook.common.base.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

internal class ChatRepositoryImpl(
    private val chatRemoteDataSource: ChatRemoteDataSource,
    private val chatLocalDataSource: ChatLocalDataSource,
    private val mapper: MessageRepoMapper,
) : ChatRepository {
    override fun sendQuestion(question: String): Flow<ApiResult<String>> = flow {
        emit(ApiResult.Loading)
        chatRemoteDataSource.sendQuestion(question)
            .catch { emit(ApiResult.Error(it)) }
            .collect {
                if (it.isEmpty()) emit(ApiResult.Empty) else emit(ApiResult.Success(it))
            }
    }

    override fun getMessages(userId: String): Flow<ApiResult<List<Message>>> = flow {
        emit(ApiResult.Loading)
        chatLocalDataSource.getMessages(userId)
            .catch { emit(ApiResult.Error(it)) }
            .collect { list ->
                emit(ApiResult.Success(list.map { mapper.toDomain(it) }))
            }
    }

    override fun saveMessage(userId: String, message: Message): Flow<ApiResult<Unit>> = flow {
        emit(ApiResult.Loading)
        chatLocalDataSource.saveMessage(userId, mapper.toRepo(message))
            .catch { emit(ApiResult.Error(it)) }
            .collect {
                emit(ApiResult.Success(Unit))
            }
    }
}
