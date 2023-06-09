package com.lgdevs.mynextbook.repository.implementation

import com.lgdevs.mynextbook.cloudservices.auth.CloudServicesAuth
import com.lgdevs.mynextbook.cloudservices.auth.CurrentUser
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.LoginParam
import com.lgdevs.mynextbook.domain.repositories.UserDataRepository
import com.lgdevs.mynextbook.repository.datasource.UserDataSourceDatastore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserDataRepositoryImplTest {
    private val dataSource: UserDataSourceDatastore = mockk()
    private val cloudServicesAuth: CloudServicesAuth = mockk()
    private val repository: UserDataRepository by lazy {
        UserDataRepositoryImpl(dataSource, cloudServicesAuth)
    }

    private val currentUser =CurrentUser(
        "1234567",
        "Teste",
        "Teste@mail.com",
        null
    )

    @Test
    fun whenUserIsRegistered_andLogin_shouldCallSignIn_withSuccess() = runTest{
        coEvery { cloudServicesAuth.isUserRegistered(any()) } returns flow { emit(true) }
        coEvery { cloudServicesAuth.signIn(any(), any()) } returns flow { emit(true) }

        val result = repository.doLogin(LoginParam(String(), String())).toList()
        coVerify(exactly = 1) { cloudServicesAuth.signIn(any(), any()) }
        coVerify(exactly = 0) { cloudServicesAuth.signUp(any(), any()) }
        assert(result.first() is ApiResult.Loading)
        assert(result.last() is ApiResult.Success)
        assert((result.last() as ApiResult.Success).data == true)
    }

    @Test
    fun whenUserIsRegistered_andLogin_shouldCallSignIn_withWrongCredentials() = runTest{
        coEvery { cloudServicesAuth.isUserRegistered(any()) } returns flow { emit(true) }
        coEvery { cloudServicesAuth.signIn(any(), any()) } returns flow { emit(false) }

        val result = repository.doLogin(LoginParam(String(), String())).toList()
        coVerify(exactly = 1) { cloudServicesAuth.signIn(any(), any()) }
        coVerify(exactly = 0) { cloudServicesAuth.signUp(any(), any()) }
        assert(result.first() is ApiResult.Loading)
        assert(result.last() is ApiResult.Success)
        assert((result.last() as ApiResult.Success).data == false)
    }

    @Test
    fun whenUserIsRegistered_andLogin_shouldCallSignIn_withError() = runTest{
        coEvery { cloudServicesAuth.isUserRegistered(any()) } returns flow { emit(true) }
        coEvery { cloudServicesAuth.signIn(any(), any()) } returns flow { throw Exception() }

        val result = repository.doLogin(LoginParam(String(), String())).toList()

        coVerify(exactly = 1) { cloudServicesAuth.signIn(any(), any()) }
        coVerify(exactly = 0) { cloudServicesAuth.signUp(any(), any()) }
        assert(result.first() is ApiResult.Loading)
        assert(result.last() is ApiResult.Error)
        assert((result.last() as ApiResult.Error).error is Exception)
    }


    @Test
    fun whenUserIsNotRegistered_andLogin_shouldCallSignUp_withSuccess() = runTest{
        coEvery { cloudServicesAuth.isUserRegistered(any()) } returns flow { emit(false) }
        coEvery { cloudServicesAuth.signUp(any(), any()) } returns flow { emit(true) }

        val result = repository.doLogin(LoginParam(String(), String())).toList()
        coVerify(exactly = 0) { cloudServicesAuth.signIn(any(), any()) }
        coVerify(exactly = 1) { cloudServicesAuth.signUp(any(), any()) }
        assert(result.first() is ApiResult.Loading)
        assert(result.last() is ApiResult.Success)
        assert((result.last() as ApiResult.Success).data == true)
    }

    @Test
    fun whenUserIsNotRegistered_andLogin_shouldCallSignUp_withError() = runTest{
        coEvery { cloudServicesAuth.isUserRegistered(any()) } returns flow { emit(false) }
        coEvery { cloudServicesAuth.signUp(any(), any()) } returns flow { throw Exception() }

        val result = repository.doLogin(LoginParam(String(), String())).toList()
        coVerify(exactly = 0) { cloudServicesAuth.signIn(any(), any()) }
        coVerify(exactly = 1) { cloudServicesAuth.signUp(any(), any()) }
        assert(result.first() is ApiResult.Loading)
        assert(result.last() is ApiResult.Error)
        assert((result.last() as ApiResult.Error).error is Exception)
    }


    @Test
    fun whenGetCurrentUser_whenCloudIsLoggedIn_shouldReturnUser() = runTest{
        coEvery { cloudServicesAuth.currentUser() } returns flow { emit(currentUser) }

        val result = repository.getCurrentUser().toList()

        assert(result.first() is ApiResult.Loading)
        assert(result.last() is ApiResult.Success)
        assert((result.last() as ApiResult.Success).data?.uuid == currentUser.uuid)
    }

    @Test
    fun whenGetCurrentUser_whenCloudIsNotLoggedIn_shouldReturnEmpty() = runTest{
        coEvery { cloudServicesAuth.currentUser() } returns flow { emit(null) }

        val result = repository.getCurrentUser().toList()

        assert(result.first() is ApiResult.Loading)
        assert(result.last() is ApiResult.Empty)
    }

    @Test
    fun whenLoginWithToken_shouldCallSignUp_withSuccess() = runTest{
        coEvery { cloudServicesAuth.signInWithProvider(any()) } returns flow { emit(true) }

        val result = repository.doLoginWithToken(String()).toList()
        assert(result.first() is ApiResult.Loading)
        assert(result.last() is ApiResult.Success)
        assert((result.last() as ApiResult.Success).data == true)
    }

    @Test
    fun whenLoginWithToken_shouldCallSignUp_withError() = runTest{
        coEvery { cloudServicesAuth.signInWithProvider(any()) } returns flow { emit(false) }

        val result = repository.doLoginWithToken(String()).toList()
        assert(result.first() is ApiResult.Loading)
        assert(result.last() is ApiResult.Success)
        assert((result.last() as ApiResult.Success).data == false)
    }

    @Test
    fun whenLoginWithToken_shouldCallSignUp_withException() = runTest{
        coEvery { cloudServicesAuth.signInWithProvider(any()) } returns flow { throw Exception() }

        val result = repository.doLoginWithToken(String()).toList()
        assert(result.first() is ApiResult.Loading)
        assert(result.last() is ApiResult.Error)
        assert((result.last() as ApiResult.Error).error is Exception)
    }
}