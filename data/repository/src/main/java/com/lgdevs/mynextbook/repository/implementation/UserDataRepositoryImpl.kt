package com.lgdevs.mynextbook.repository.implementation

import com.lgdevs.mynextbook.cloudservices.auth.CloudServicesAuth
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.LoginParam
import com.lgdevs.mynextbook.domain.model.User
import com.lgdevs.mynextbook.domain.repositories.UserDataRepository
import com.lgdevs.mynextbook.repository.datasource.UserDataSourceDatastore
import com.lgdevs.mynextbook.repository.mapper.CloudServiceMapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

internal class UserDataRepositoryImpl(
    private val userDataRepositoryImpl: UserDataSourceDatastore,
    private val cloudServicesAuth: CloudServicesAuth,
) : UserDataRepository {
    override suspend fun doLogin(params: LoginParam): Flow<ApiResult<Boolean>> = flow {
        emit(ApiResult.Loading)
        cloudServicesAuth.isUserRegistered(params.email)
            .catch { emit(ApiResult.Error(it)) }
            .collect { isRegistered ->
                if (isRegistered) {
                    cloudServicesAuth.signIn(params.email, params.password)
                        .catch { emit(ApiResult.Error(it)) }
                        .collect { emit(ApiResult.Success(it)) }
                } else {
                    cloudServicesAuth.signUp(params.email, params.password)
                        .catch { emit(ApiResult.Error(it)) }
                        .collect { emit(ApiResult.Success(it)) }
                }
            }
    }

    override suspend fun getCurrentUser(): Flow<ApiResult<User>> = flow {
        emit(ApiResult.Loading)
        cloudServicesAuth.currentUser()
            .catch { emit(ApiResult.Error(it)) }
            .collect { user ->
                user?.let {
                    emit(ApiResult.Success(it.toDomain()))
                } ?: emit(ApiResult.Empty)
            }
    }

    override suspend fun updatePreferences(email: String) {
        return userDataRepositoryImpl.updateEmail(email)
    }

    override suspend fun loadPreferences(): Flow<String> {
        return userDataRepositoryImpl.loadPreferences()
    }

    override suspend fun doLoginWithToken(token: String): Flow<ApiResult<Boolean>> = flow {
        emit(ApiResult.Loading)
        cloudServicesAuth.signInWithProvider(token)
            .catch { emit(ApiResult.Error(it)) }
            .collect { emit(ApiResult.Success(it)) }
    }
}
