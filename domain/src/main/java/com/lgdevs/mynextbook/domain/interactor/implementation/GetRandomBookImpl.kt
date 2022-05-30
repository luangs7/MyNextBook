package com.lgdevs.mynextbook.domain.interactor.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetRandomBook
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.GetBookParams
import com.lgdevs.mynextbook.domain.repositories.BookRemoteRepository
import kotlinx.coroutines.flow.Flow

class GetRandomBookImpl(
    private val repository: BookRemoteRepository
) : GetRandomBook() {
    override suspend fun execute(params: GetBookParams): Flow<ApiResult<Book>> =
        repository.getRandomBook(params)
}