package com.lgdevs.mynextbook.domain.interactor.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetFavoriteBooks
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookLocalRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteBooksImpl(
    private val repository: BookLocalRepository
) : GetFavoriteBooks() {
    override suspend fun execute(params: Unit): Flow<ApiResult<List<Book>>> =
        repository.getFavorites()
}