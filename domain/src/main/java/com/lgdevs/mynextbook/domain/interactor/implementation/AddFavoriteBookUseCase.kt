package com.lgdevs.mynextbook.domain.interactor.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import kotlinx.coroutines.flow.Flow


fun interface AddFavoriteBookUseCase: suspend (Book) -> Flow<ApiResult<Unit>>