package com.lgdevs.mynextbook.domain.repositories

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.LoginParam
import com.lgdevs.mynextbook.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun doLogin(loginParam: LoginParam): Flow<ApiResult<Boolean>>
    suspend fun getCurrentUser(): Flow<ApiResult<User>>
    suspend fun updatePreferences(email: String): Flow<Unit>
    suspend fun loadPreferences(): Flow<String>
}