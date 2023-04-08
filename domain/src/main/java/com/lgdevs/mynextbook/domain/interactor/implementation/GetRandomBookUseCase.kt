package com.lgdevs.mynextbook.domain.interactor.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.AppPreferences
import kotlinx.coroutines.flow.Flow

fun interface GetRandomBookUseCase: OnGetRandomBook

typealias OnGetRandomBook = suspend (AppPreferences) -> Flow<ApiResult<Book>>