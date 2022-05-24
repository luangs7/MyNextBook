package com.lgdevs.mynextbook.repository.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookLocalRepository
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceLocal
import com.lgdevs.mynextbook.repository.model.toDomain
import com.lgdevs.mynextbook.repository.model.toRepo
import kotlinx.coroutines.flow.*

class BookLocalRepositoryImpl(
    private val dataSourceLocal: BookDataSourceLocal
) : BookLocalRepository {
    override suspend fun addFavorites(book: Book): Flow<Unit> {
        return dataSourceLocal.setFavoriteBook(book.toRepo())
    }

    override suspend fun getFavorites(): Flow<ApiResult<List<Book>>> = flow {
        emit(ApiResult.Loading)
        dataSourceLocal.getFavoritesBooks()
            .catch { emit(ApiResult.Error(it)) }
            .collect { response ->
                val result = if (response.isEmpty()) {
                    ApiResult.Empty
                } else {
                    ApiResult.Success(response.map { it.toDomain() })
                }
                emit(result)
            }
    }

    override suspend fun removeFavorite(book: Book): Flow<Unit> {
        return dataSourceLocal.removeFavoriteBook(book.toRepo())
    }
}
