package com.lgdevs.mynextbook.domain.repositories

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.AppPreferences
import kotlinx.coroutines.flow.Flow

interface BookRemoteRepository {
    suspend fun getRandomBook(params: AppPreferences): Flow<ApiResult<Book>>
}