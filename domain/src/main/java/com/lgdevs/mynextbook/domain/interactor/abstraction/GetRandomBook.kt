package com.lgdevs.mynextbook.domain.interactor.abstraction

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.UseCase
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.AppPreferences
import kotlinx.coroutines.flow.Flow

abstract class GetRandomBook: UseCase<AppPreferences, Flow<ApiResult<Book>>>()