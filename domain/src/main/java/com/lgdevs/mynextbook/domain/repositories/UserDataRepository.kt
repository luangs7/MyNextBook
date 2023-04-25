package com.lgdevs.mynextbook.domain.repositories

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.LoginParam
import com.lgdevs.mynextbook.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    fun doLogin(loginParam: LoginParam): Flow<ApiResult<Boolean>>
    fun doLoginWithToken(token: String): Flow<ApiResult<Boolean>>
    fun getCurrentUser(): Flow<ApiResult<User>>
    suspend fun updatePreferences(email: String): Unit
    fun loadPreferences(): Flow<String>
}
