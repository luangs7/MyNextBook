package com.lgdevs.mynextbook.repository.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookLocalRepository
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceLocal
import com.lgdevs.mynextbook.repository.mapper.BookRepoMapper
import kotlinx.coroutines.flow.*

internal class BookLocalRepositoryImpl(
    private val dataSourceLocal: BookDataSourceLocal,
    private val mapper: BookRepoMapper
) : BookLocalRepository {
    override suspend fun addFavorites(book: Book): Flow<ApiResult<Unit>> = flow {
        emit(ApiResult.Loading)
        dataSourceLocal.setFavoriteBook(mapper.toRepo(book))
            .catch { emit(ApiResult.Error(it)) }
            .collect {
                emit(ApiResult.Success(it))
            }
    }

    override suspend fun getFavorites(): Flow<ApiResult<List<Book>>> = flow {
        emit(ApiResult.Loading)
        dataSourceLocal.getFavoritesBooks()
            .catch { emit(ApiResult.Error(it)) }
            .collect { response ->
                val result = if (response.isEmpty()) {
                    ApiResult.Empty
                } else {
                    ApiResult.Success(response.map { mapper.toDomain(it) })
                }
                emit(result)
            }
    }

    override suspend fun removeFavorite(book: Book): Flow<ApiResult<Unit>> = flow {
        emit(ApiResult.Loading)
        dataSourceLocal.removeFavoriteBook(mapper.toRepo(book))
            .catch { emit(ApiResult.Error(it)) }
            .collect {
                emit(ApiResult.Success(it))
            }
    }
}
