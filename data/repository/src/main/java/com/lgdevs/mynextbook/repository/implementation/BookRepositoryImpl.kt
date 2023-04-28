package com.lgdevs.mynextbook.repository.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookRepository
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceLocal
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceRemote
import com.lgdevs.mynextbook.repository.mapper.BookRepoMapper
import com.lgdevs.mynextbook.repository.mapper.PreferencesRepoMapper
import kotlinx.coroutines.flow.*

internal class BookRepositoryImpl(
    private val dataSourceLocal: BookDataSourceLocal,
    private val mapper: BookRepoMapper,
    private val dataSourceRemote: BookDataSourceRemote,
    private val prefMapper: PreferencesRepoMapper
) : BookRepository {
    override suspend fun addFavorites(book: Book, userId: String): Flow<ApiResult<Unit>> = flow {
        emit(ApiResult.Loading)
        dataSourceLocal.setFavoriteBook(mapper.toRepo(book), userId)
            .catch { emit(ApiResult.Error(it)) }
            .collect {
                emit(ApiResult.Success(it))
            }
    }

    override suspend fun getFavorites(userId: String): Flow<ApiResult<List<Book>>> = flow {
        emit(ApiResult.Loading)
        dataSourceLocal.getFavoritesBooks(userId)
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

    override suspend fun getRandomBook(params: AppPreferences): Flow<ApiResult<Book>> = flow {
        emit(ApiResult.Loading)
        dataSourceRemote.getBooksFromQuery(prefMapper.toRepo(params))
            .catch { emit(ApiResult.Error(it)) }
            .transform { book ->
                dataSourceLocal.getFavoriteBook(book.id).collect { favorite ->
                    emit(book.also { it.isFavorited = favorite != null })
                }
            }.collect {
                emit(ApiResult.Success(mapper.toDomain(it)))
            }
    }
}
