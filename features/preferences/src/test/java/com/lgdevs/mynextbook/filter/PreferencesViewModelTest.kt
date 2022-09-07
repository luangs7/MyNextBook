package com.lgdevs.mynextbook.filter

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetPreferences
import com.lgdevs.mynextbook.domain.interactor.abstraction.UpdatePreferences
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.filter.viewmodel.PreferencesViewModel
import com.lgdevs.mynextbook.tests.BaseTest
import com.lgdevs.mynextbook.tests.toScope
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PreferencesViewModelTest : BaseTest() {

    private val setPreferences: UpdatePreferences = mockk()
    private val getPreferences: GetPreferences = mockk()
    private val viewModel: PreferencesViewModel by lazy { PreferencesViewModel(setPreferences, getPreferences) }

    @Test
    fun `when setPreferences() is called should add item to preferences system and return success`() = runTest {
        coEvery { setPreferences.execute(any()) } returns flow {
            emit(Unit)
        }

        val emittedResults = mutableListOf<ViewState<AppPreferences>>()

        viewModel.addPreferences.onEach(emittedResults::add).launchIn(testScheduler.toScope())
        viewModel.setPreferences(false, "keyword", false)
        runCurrent()

        coVerify(exactly = 1) {
            setPreferences.execute(any())
        }
        assert(emittedResults.last() is ViewState.Success)
    }

    @Test
    fun `when getPreferences() is called and has preferences should return item with success`() = runTest {
        val pref = AppPreferences(false, "keyword", false, null)
        coEvery { getPreferences.execute(Unit) } returns flow {
            emit(pref)
        }

        val emittedResults = mutableListOf<ViewState<AppPreferences>>()
        viewModel.getPreferences().onEach(emittedResults::add).launchIn(testScheduler.toScope())
        runCurrent()

        coVerify(exactly = 1) { getPreferences.execute(Unit) }
        assert((emittedResults.last() as? ViewState.Success)?.result?.keyword == pref.keyword)
    }

    @Test
    fun `when getPreferences() is called and throws exception should return error`() = runTest {
        coEvery { getPreferences.execute(Unit) } throws Exception()

        val emittedResults = mutableListOf<ViewState<AppPreferences>>()

        viewModel.getPreferences().onEach(emittedResults::add).launchIn(testScheduler.toScope())
        runCurrent()

        coVerify(exactly = 1) { getPreferences.execute(Unit) }
        assert(emittedResults.last() is ViewState.Error)
    }
}