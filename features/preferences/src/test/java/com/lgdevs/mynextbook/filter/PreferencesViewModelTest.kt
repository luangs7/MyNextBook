package com.lgdevs.mynextbook.filter

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.implementation.GetPreferencesUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.UpdatePreferencesUseCase
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.User
import com.lgdevs.mynextbook.filter.viewmodel.PreferencesViewModel
import com.lgdevs.mynextbook.tests.BaseTest
import com.lgdevs.mynextbook.tests.toScope
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class PreferencesViewModelTest : BaseTest() {
    private val getCurrentUser: GetUserUseCase = mockk()
    private val setPreferences: UpdatePreferencesUseCase = mockk()
    private val getPreferences: GetPreferencesUseCase = mockk()
    private val viewModel: PreferencesViewModel by lazy { PreferencesViewModel(setPreferences, getPreferences, getCurrentUser) }
    private val userId = Random.nextInt().toString()
    private val user = User(userId, "Teste", "teste@adbc.com",null)

    @Test
    fun `when setPreferences() is called should add item to preferences system and return success`() = runTest {
        coEvery { setPreferences(any(), any()) } returns Unit
        coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

        val emittedResults = mutableListOf<ViewState<AppPreferences>>()

        viewModel.addPreferences.onEach(emittedResults::add).launchIn(testScheduler.toScope())
        viewModel.setPreferences(false, "keyword", false)
        runCurrent()

        coVerify(exactly = 1) {
            setPreferences(any(), any())
        }
        assert(emittedResults.last() is ViewState.Success)
    }

    @Test
    fun `when getPreferences() is called and has preferences should return item with success`() = runTest {
        val pref = AppPreferences(false, "keyword", false, null)
        coEvery { getPreferences(any()) } returns flow {
            emit(pref)
        }
        coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

        val emittedResults = mutableListOf<ViewState<AppPreferences>>()
        viewModel.getPreferences().onEach(emittedResults::add).launchIn(testScheduler.toScope())
        runCurrent()

        coVerify(exactly = 1) { getPreferences(userId) }
        assert((emittedResults.last() as? ViewState.Success)?.result?.keyword == pref.keyword)
    }

    @Test
    fun `when getPreferences() is called and throws exception should return error`() = runTest {
        coEvery { getPreferences(any()) } throws Exception()
        coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

        val emittedResults = mutableListOf<ViewState<AppPreferences>>()

        viewModel.getPreferences().onEach(emittedResults::add).launchIn(testScheduler.toScope())
        runCurrent()

        coVerify(exactly = 1) { getPreferences(userId) }
        assert(emittedResults.last() is ViewState.Error)
    }
}