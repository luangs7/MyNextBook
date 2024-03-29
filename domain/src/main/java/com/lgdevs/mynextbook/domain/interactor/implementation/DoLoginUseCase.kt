package com.lgdevs.mynextbook.domain.interactor.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.LoginParam
import kotlinx.coroutines.flow.Flow

fun interface DoLoginUseCase: suspend (LoginParam) -> Flow<ApiResult<Boolean>>