package com.lgdevs.mynextbook.repository.datasource

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.repository.model.BookData
import kotlinx.coroutines.flow.Flow

interface BookDataSourceLocal {
    suspend fun setFavoriteBook(book: BookData, userId: String): Flow<Unit>
    suspend fun removeFavoriteBook(book: BookData): Flow<Unit>
    suspend fun getFavoritesBooks(userId: String): Flow<List<BookData>>
    suspend fun getFavoriteBook(id: String): Flow<BookData?>
}