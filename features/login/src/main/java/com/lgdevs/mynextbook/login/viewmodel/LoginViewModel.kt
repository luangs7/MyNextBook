package com.lgdevs.mynextbook.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.signin.internal.SignInClientImpl
import com.lgdevs.mynextbook.cloudservices.remoteconfig.CloudServicesRemoteConfig
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.implementation.DoLoginUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.DoLoginWithTokenUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetEmailLoginUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.SetEmailLoginUseCase
import com.lgdevs.mynextbook.domain.model.LoginParam
import com.lgdevs.mynextbook.domain.model.User
import com.lgdevs.mynextbook.remoteconfig.LOGIN_WITH_GOOGLE_BUTTON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModel(
    private val doLoginUseCase: DoLoginUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val saveEmail: SetEmailLoginUseCase,
    private val getEmail: GetEmailLoginUseCase,
    private val doLoginWithTokenUseCase: DoLoginWithTokenUseCase,
    private val cloudServicesRemoteConfig: CloudServicesRemoteConfig
) : ViewModel() {

    private val userSharedFlow: MutableSharedFlow<Unit> = MutableSharedFlow(replay = 1)

    val userFlow = userSharedFlow.flatMapLatest {
        onGetUser().catch { throwable -> emit(ViewState.Error(throwable)) }
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)


    fun getUser() = userSharedFlow.tryEmit(Unit)

    fun onGetEmail() = flow<String?> {
        getEmail()
            .catch { emit(null) }
            .collect {
                emit(it)
            }
    }

    private fun onGetUser() = flow<ViewState<User?>> {
        getUserUseCase()
            .catch { emit(ViewState.Error(it)) }
            .collect {
                val result = when (it){
                    ApiResult.Empty -> ViewState.Empty
                    is ApiResult.Error ->  ViewState.Error(it.error)
                    ApiResult.Loading -> ViewState.Loading
                    is ApiResult.Success -> ViewState.Success(it.data)
                }
                emit(result)
            }
    }

    suspend fun doLogin(email: String, password: String): Flow<ViewState<Boolean>> = flow {
        doLoginUseCase(LoginParam(email, password))
            .catch { emit(ViewState.Error(it)) }
            .collect {
                val result = when(it){
                    ApiResult.Empty -> ViewState.Empty
                    is ApiResult.Error -> ViewState.Error(it.error)
                    ApiResult.Loading -> ViewState.Loading
                    is ApiResult.Success -> {
                        saveEmail(email).collect()
                        ViewState.Success(it.data ?: false)
                    }
                }

                emit(result)
            }
    }

    suspend fun doLoginWithToken(email:String, token: String): Flow<ViewState<Boolean>> = flow {
        doLoginWithTokenUseCase(token)
            .catch { emit(ViewState.Error(it)) }
            .collect {
                val result = when(it){
                    ApiResult.Empty -> ViewState.Empty
                    is ApiResult.Error -> ViewState.Error(it.error)
                    ApiResult.Loading -> ViewState.Loading
                    is ApiResult.Success -> {
                        saveEmail(email).collect()
                        ViewState.Success(it.data ?: false)
                    }
                }
                emit(result)
            }
    }

    fun showGoogleButton() = flow<Boolean>{
        cloudServicesRemoteConfig.run {
            fetch()
            emit(getBoolean(LOGIN_WITH_GOOGLE_BUTTON))
        }
    }.flowOn(Dispatchers.IO)
}