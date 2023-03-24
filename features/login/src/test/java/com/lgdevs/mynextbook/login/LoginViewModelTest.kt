package com.lgdevs.mynextbook.login

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.common.dispatcher.CoroutineDispatcherProvider
import com.lgdevs.mynextbook.domain.interactor.implementation.DoLoginUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.DoLoginWithTokenUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetEmailLoginUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.SetEmailLoginUseCase
import com.lgdevs.mynextbook.login.viewmodel.LoginViewModel
import com.lgdevs.mynextbook.remoteconfig.LOGIN_WITH_GOOGLE_BUTTON
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    private val getUserUseCase = mockk<GetUserUseCase>()
    private val doLoginUseCase = mockk<DoLoginUseCase>()
    private val doLoginWithTokenUseCase = mockk<DoLoginWithTokenUseCase>()
    private val cloudServices = mockk<CloudServicesHolder>()
    private val saveEmailUseCase = mockk<SetEmailLoginUseCase>()
    private val getEmailUseCase = mockk<GetEmailLoginUseCase>()
    private val dispatcherManager = mockk<CoroutineDispatcherProvider>()

    private val viewModel: LoginViewModel by lazy {
        LoginViewModel(
            doLoginUseCase,
            getUserUseCase,
            saveEmailUseCase,
            getEmailUseCase,
            doLoginWithTokenUseCase,
            cloudServices,
            dispatcherManager,
        )
    }

    @Before
    fun before() {
        every { dispatcherManager.invoke() } returns Dispatchers.IO
    }

    @Test
    fun onGetGoogleButton() = runTest {
        coEvery { cloudServices.getRemoteConfig().fetch() } returns true
        coEvery { cloudServices.getRemoteConfig().getBoolean(LOGIN_WITH_GOOGLE_BUTTON) } returns true
        val toggle = viewModel.showGoogleButton().first()
        assertEquals(true, toggle)
    }

    @Test
    fun doLoginShouldEmitASuccessStateWithTrue() = runTest {
        coEvery { doLoginUseCase(any()) } returns
            flowOf(ApiResult.Success(true))
        coEvery { saveEmailUseCase(any()) } returns Unit
        val loginFlow = viewModel.doLogin("test@test.com", "password")
        val state = loginFlow.first()
        assertTrue(state is ViewState.Success && state.result)
        coVerify(exactly = 1) { saveEmailUseCase(any()) }
    }

    @Test
    fun doLoginShouldEmitAnErrorStateWithInvalidCredentials() = runTest {
        coEvery { doLoginUseCase(any()) } returns
            flowOf(ApiResult.Empty)
        val loginFlow = viewModel.doLogin("test@test.com", "password")
        val state = loginFlow.first()
        assertTrue(state is ViewState.Empty)
        coVerify(exactly = 0) { saveEmailUseCase(any()) }
    }

    @Test
    fun doLoginShouldEmitAnErrorState() = runTest {
        coEvery { doLoginUseCase(any()) } returns
            flowOf(ApiResult.Error(Exception()))
        val loginFlow = viewModel.doLogin("test@test.com", "password")
        val state = loginFlow.first()
        assertTrue(state is ViewState.Error)
        coVerify(exactly = 0) { saveEmailUseCase(any()) }
    }

    @Test
    fun doLoginWithTokenShouldEmitASuccessStateWithTrue() = runTest {
        coEvery { doLoginWithTokenUseCase(any()) } returns flowOf(ApiResult.Success(true))
        coEvery { saveEmailUseCase(any()) } returns Unit
        val loginFlow = viewModel.doLoginWithToken("test@test.com", "token")
        val state = loginFlow.first()
        assertTrue(state is ViewState.Success && state.result)
        coVerify(exactly = 1) { saveEmailUseCase(any()) }
    }

    @Test
    fun doLoginWithTokenShouldEmitASuccessStateWithException() = runTest {
        coEvery { doLoginWithTokenUseCase(any()) } returns
            flowOf(ApiResult.Error(Exception()))
        val loginFlow = viewModel.doLoginWithToken("test@test.com", "token")
        val state = loginFlow.first()
        assertTrue(state is ViewState.Error)
        coVerify(exactly = 0) { saveEmailUseCase(any()) }
    }

    @Test
    fun doLoginWithTokenShouldEmitASuccessStateWithInvalidCredentials() = runTest {
        coEvery { doLoginWithTokenUseCase(any()) } returns
            flowOf(ApiResult.Empty)
        val loginFlow = viewModel.doLoginWithToken("test@test.com", "token")
        val state = loginFlow.first()
        assertTrue(state is ViewState.Empty)
        coVerify(exactly = 0) { saveEmailUseCase(any()) }
    }
}
