package com.lgdevs.mynextbook.domain.interactor.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.abstraction.RemoveBookFromFavorite
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookLocalRepository
import kotlinx.coroutines.flow.Flow

class RemoveBookFromFavoriteImpl(
    private val repository: BookLocalRepository
) : RemoveBookFromFavorite() {
    override suspend fun execute(params: Book): Flow<ApiResult<Unit>> =
        repository.removeFavorite(params)
}