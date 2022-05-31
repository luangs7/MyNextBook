package com.lgdevs.mynextbook.repository.implementation


import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.repositories.BookRemoteRepository
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceRemote
import com.lgdevs.mynextbook.repository.mapper.BookRepoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

internal class BookRemoteRepositoryImpl(
    private val dataSourceRemote: BookDataSourceRemote,
    private val mapper: BookRepoMapper
): BookRemoteRepository {
    override suspend fun getRandomBook(params: AppPreferences): Flow<ApiResult<Book>> = flow {
        emit(ApiResult.Loading)
        dataSourceRemote.getBooksFromQuery(mapper.toRepo(params))
            .catch { emit(ApiResult.Error(it)) }
            .collect { emit(ApiResult.Success(mapper.toDomain(it))) }
    }
}