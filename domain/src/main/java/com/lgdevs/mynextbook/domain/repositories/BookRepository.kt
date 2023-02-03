package com.lgdevs.mynextbook.domain.repositories

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun getFavorites(): Flow<ApiResult<List<Book>>>
    suspend fun addFavorites(book: Book): Flow<ApiResult<Unit>>
    suspend fun removeFavorite(book: Book): Flow<ApiResult<Unit>>
    suspend fun getRandomBook(params: AppPreferences): Flow<ApiResult<Book>>

}