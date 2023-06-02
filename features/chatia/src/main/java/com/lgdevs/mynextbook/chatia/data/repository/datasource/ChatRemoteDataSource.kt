package com.lgdevs.mynextbook.chatia.data.repository.datasource

import kotlinx.coroutines.flow.Flow

interface ChatRemoteDataSource {
    fun sendQuestion(message: String): Flow<String>
}
