package com.lgdevs.mynextbook.remote.datasource

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.remote.model.toRepo
import com.lgdevs.mynextbook.remote.service.BookService
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceRemote
import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.BookParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BookDataSourceRemoteImpl(
    private val service: BookService
) : BookDataSourceRemote {
    override suspend fun getBooksFromQuery(bookParams: BookParams): Flow<BookData> = flow {
            val response = service.getBooks(bookParams.keyword ?: String(), bookParams.language)
            if (response.isSuccessful) {
                response.body()?.toRepo()?.random()?.let { item ->
                    emit(item)
                } ?: throw Exception()
            } else {
                throw Exception(response.errorBody()?.toString())
            }
        }
}