package com.lgdevs.mynextbook.domain.interactor.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.User
import kotlinx.coroutines.flow.Flow

fun interface GetUserUseCase: OnGetUser

typealias OnGetUser = suspend () -> Flow<ApiResult<User>>