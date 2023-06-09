package com.lgdevs.mynextbook.login.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.common.dispatcher.CoroutineDispatcherProvider
import com.lgdevs.mynextbook.domain.model.LoginParam
import com.lgdevs.mynextbook.domain.model.User
import com.lgdevs.mynextbook.login.analytics.LoginAnalytics
import com.lgdevs.mynextbook.login.holder.cloudservices.CloudServicesHolder
import com.lgdevs.mynextbook.login.holder.usecase.LoginInteractorHolder
import com.lgdevs.mynextbook.remoteconfig.LOGIN_WITH_GOOGLE_BUTTON
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModel(
    private val loginInteractorHolder: LoginInteractorHolder,
    private val cloudServicesHolder: CloudServicesHolder,
    private val dispatcher: CoroutineDispatcherProvider,
    private val loginAnalytics: LoginAnalytics,
) : ViewModel() {

    private val userSharedFlow: MutableSharedFlow<Unit> = MutableSharedFlow(replay = 1)

    val userFlow = userSharedFlow.flatMapLatest {
        onGetUser().catch { throwable -> emit(ViewState.Error(throwable)) }
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)
    fun getUser() = userSharedFlow.tryEmit(Unit)
    fun onGetEmail() = flow<String?> {
        loginInteractorHolder.getEmailUseCase().invoke()
            .catch { emit(null) }
            .collect {
                emit(it)
            }
    }.flowOn(dispatcher.invoke())
    init {
        viewModelScope.launch {
            loginAnalytics.onEvent(LoginAnalytics.LOGIN_SCREEN_VIEW, Bundle.EMPTY)
        }
    }

    @TestOnly
    private fun onGetUser() = flow<ViewState<User?>> {
        loginInteractorHolder.getUserUseCase().invoke()
            .catch { emit(ViewState.Error(it)) }
            .collect {
                val result = when (it) {
                    ApiResult.Empty -> ViewState.Empty
                    is ApiResult.Error -> ViewState.Error(it.error)
                    ApiResult.Loading -> ViewState.Loading
                    is ApiResult.Success -> {
                        loginAnalytics.run {
                            it.data?.let { user ->
                                setUserId(user.uuid)
                                loginAnalytics.onEvent(LoginAnalytics.LOGIN_USER, createParams(user))
                            }
                        }
                        ViewState.Success(it.data)
                    }
                }
                emit(result)
            }
    }.flowOn(dispatcher.invoke())

    fun doLogin(email: String, password: String): Flow<ViewState<Boolean>> = flow<ViewState<Boolean>> {
        loginAnalytics.onEvent(LoginAnalytics.BUTTON_LOGIN_ENTER, Bundle.EMPTY)
        loginInteractorHolder.getLoginUseCase().invoke(LoginParam(email, password))
            .catch { emit(ViewState.Error(it)) }
            .collect {
                val result = when (it) {
                    ApiResult.Empty -> ViewState.Empty
                    is ApiResult.Error -> {
                        loginAnalytics.logException(LoginAnalytics.LOGIN_WITH_ERROR, it.error)
                        ViewState.Error(it.error)
                    }
                    ApiResult.Loading -> ViewState.Loading
                    is ApiResult.Success -> {
                        loginInteractorHolder.getSaveEmailUseCase().invoke(email)
                        ViewState.Success(it.data ?: false)
                    }
                }

                emit(result)
            }
    }.flowOn(dispatcher.invoke())

    fun doLoginWithToken(email: String, token: String): Flow<ViewState<Boolean>> = flow<ViewState<Boolean>> {
        loginAnalytics.onEvent(LoginAnalytics.BUTTON_LOGIN_ENTER_GOOGLE, Bundle.EMPTY)
        loginInteractorHolder.getLoginWithTokenUseCase().invoke(token)
            .catch { emit(ViewState.Error(it)) }
            .collect {
                val result = when (it) {
                    ApiResult.Empty -> ViewState.Empty
                    is ApiResult.Error -> {
                        loginAnalytics.logException(LoginAnalytics.LOGIN_WITH_GOOGLE_ERROR, it.error)
                        ViewState.Error(it.error)
                    }
                    ApiResult.Loading -> ViewState.Loading
                    is ApiResult.Success -> {
                        loginInteractorHolder.getSaveEmailUseCase().invoke(email)
                        ViewState.Success(it.data ?: false)
                    }
                }
                emit(result)
            }
    }.flowOn(dispatcher.invoke())

    fun showGoogleButton() = flow<Boolean> {
        cloudServicesHolder.getRemoteConfig().run {
            fetch()
            emit(getBoolean(LOGIN_WITH_GOOGLE_BUTTON))
        }
    }.flowOn(dispatcher.invoke())
}
