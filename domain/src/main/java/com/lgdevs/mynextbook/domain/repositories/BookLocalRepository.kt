package com.lgdevs.mynextbook.domain.repositories

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookLocalRepository {
    suspend fun getFavorites(): Flow<ApiResult<List<Book>>>
    suspend fun addFavorites(book: Book): Flow<Unit>
    suspend fun removeFavorite(book: Book): Flow<Unit>
}