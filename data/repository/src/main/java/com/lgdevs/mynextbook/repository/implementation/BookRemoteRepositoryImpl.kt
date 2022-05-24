package com.lgdevs.mynextbook.repository.implementation


import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.GetBookParams
import com.lgdevs.mynextbook.domain.repositories.BookRemoteRepository
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceRemote
import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.toDomain
import com.lgdevs.mynextbook.repository.model.toRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class BookRemoteRepositoryImpl(
    private val dataSourceRemote: BookDataSourceRemote
): BookRemoteRepository {
    override suspend fun getRandomBook(params: GetBookParams): Flow<ApiResult<Book>> = flow {
        emit(ApiResult.Loading)
        dataSourceRemote.getBooksFromQuery(params.toRepo())
            .catch { emit(ApiResult.Error(it)) }
            .collect { emit(ApiResult.Success(it.toDomain())) }
    }
}