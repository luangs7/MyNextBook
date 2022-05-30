package com.lgdevs.mynextbook.domain.interactor.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.abstraction.AddFavoriteBook
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookLocalRepository
import kotlinx.coroutines.flow.Flow

class AddFavoriteBookImpl(
    private val repository: BookLocalRepository
) : AddFavoriteBook() {
    override suspend fun execute(params: Book): Flow<ApiResult<Unit>> =
        repository.addFavorites(book = params)
}