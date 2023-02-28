package com.lgdevs.mynextbook.domain.interactor.implementation

import android.graphics.Color
import android.provider.Settings
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.User
import com.lgdevs.mynextbook.domain.repositories.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException

/*
    this is just an example of
    how to use functional usecase (SAM) with "business logic"
    the logic itself its useless
 */
fun interface AddFavoriteBookUseCase : suspend (Book, User?) -> Flow<ApiResult<Unit>>


suspend inline fun favoriteBookFactory(
    book: Book,
    user: User?,
    repository: BookRepository
): Flow<ApiResult<Unit>> {

    user?.let {
        return repository.addFavorites(book, it.uuid)
    }
    return flow { emit(ApiResult.Error(InvalidParameterException())) }
}
