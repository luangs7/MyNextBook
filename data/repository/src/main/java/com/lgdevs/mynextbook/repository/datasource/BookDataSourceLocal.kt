package com.lgdevs.mynextbook.repository.datasource

import com.lgdevs.mynextbook.repository.model.BookData
import kotlinx.coroutines.flow.Flow

interface BookDataSourceLocal {
    fun setFavoriteBook(book: BookData, userId: String): Flow<Unit>
    fun removeFavoriteBook(book: BookData): Flow<Unit>
    fun getFavoritesBooks(userId: String): Flow<List<BookData>>
    fun getFavoriteBook(id: String): Flow<BookData?>
}
