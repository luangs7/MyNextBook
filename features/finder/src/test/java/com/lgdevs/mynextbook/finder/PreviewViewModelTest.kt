package com.lgdevs.mynextbook.finder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.abstraction.AddFavoriteBook
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetPreferences
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetRandomBook
import com.lgdevs.mynextbook.domain.interactor.abstraction.RemoveBookFromFavorite
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.finder.preview.viewmodel.PreviewViewModel
import com.lgdevs.mynextbook.tests.BaseTest
import com.lgdevs.mynextbook.tests.CoroutineTestRule
import com.lgdevs.mynextbook.tests.toScope
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PreviewViewModelTest : BaseTest() {

    private val getPreferences: GetPreferences = mockk()
    private val getRandomBook: GetRandomBook = mockk()
    private val addFavoriteBook: AddFavoriteBook = mockk()
    private val removeBookFromFavorite: RemoveBookFromFavorite = mockk()
    private val viewModel: PreviewViewModel by lazy {
        PreviewViewModel(
            getPreferences,
            getRandomBook,
            addFavoriteBook,
            removeBookFromFavorite,
            testDispatcher
        )
    }


    private val appPreferences = AppPreferences(
        isEbook = false,
        isPortuguese = false,
        keyword = String(),
        subject = String()
    )

    private val commonValidation = {
        coVerify(exactly = 1) {
            getPreferences.execute(Unit)
            getRandomBook.execute(any())
        }
    }


    @Test
    fun `when getRandomBook() is called and data is valid, should return loading and success status and item id should be valid`() =
        runTest {
            coEvery { getPreferences.execute(Unit) } returns flow {
                emit(appPreferences)
            }
            coEvery { getRandomBook.execute(any()) } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Success(Book(id = "1234")))
            }

            val emittedResults = mutableListOf<ViewState<Book>>()
            viewModel.randomBookFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())

            viewModel.randomBook()

            runCurrent()

            assert(emittedResults.size == 2)
            assert(emittedResults.first() is ViewState.Loading)
            assert(emittedResults.last() is ViewState.Success)
            assert((emittedResults.last() as ViewState.Success).result.id == "1234")
            commonValidation.invoke()
        }


    @Test
    fun `when getRandomBook() is called and data is empty, should return loading and empty status`() =
        runTest {
            coEvery { getPreferences.execute(Unit) } returns flow {
                emit(appPreferences)
            }
            coEvery { getRandomBook.execute(any()) } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Empty)
            }

            val emittedResults = mutableListOf<ViewState<Book>>()
            viewModel.randomBookFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())

            viewModel.randomBook()

            runCurrent()

            assert(emittedResults.size == 2)
            assert(emittedResults.first() is ViewState.Loading)
            assert(emittedResults.last() is ViewState.Empty)
            commonValidation.invoke()
        }

    @Test
    fun `when getRandomBook() is called and data is invalid, should return loading and error`() =
        runTest {
            coEvery { getPreferences.execute(Unit) } returns flow {
                emit(appPreferences)
            }
            coEvery { getRandomBook.execute(any()) } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Error(Exception()))
            }

            val emittedResults = mutableListOf<ViewState<Book>>()
            viewModel.randomBookFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())

            viewModel.randomBook()

            runCurrent()

            assert(emittedResults.size == 2)
            assert(emittedResults.first() is ViewState.Loading)
            assert(emittedResults.last() is ViewState.Error)
            commonValidation.invoke()
        }

    @Test
    fun `when getRandomBook() is called and throws error, should return error`() = runTest {
        coEvery { getPreferences.execute(Unit) } returns flow {
            emit(appPreferences)
        }
        coEvery { getRandomBook.execute(any()) } throws Exception()

        val emittedResults = mutableListOf<ViewState<Book>>()
        viewModel.randomBookFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())

        viewModel.randomBook()

        runCurrent()

        assert(emittedResults.size == 1)
        assert(emittedResults.last() is ViewState.Error)
        commonValidation.invoke()
    }


    @Test
    fun `when itemFavoriteBook() is called and book is favorite, should call removeBookFromFavorite with result success`() =
        runTest {
            coEvery {
                removeBookFromFavorite.execute(any())
            } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Success(Unit))
            }

            val emittedResults = mutableListOf<ViewState<Unit?>>()
            viewModel.bookFavoriteFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())

            viewModel.itemFavoriteBook(Book(id = "1234", isFavorited = true))

            runCurrent()

            coVerify(exactly = 1) { removeBookFromFavorite.execute(any()) }
            coVerify(exactly = 0) { addFavoriteBook.execute(any()) }

            assert(emittedResults.size == 2)
            assert(emittedResults.first() is ViewState.Loading)
            assert(emittedResults.last() is ViewState.Success)
        }

    @Test
    fun `when itemFavoriteBook() is called and book is favorite, should call removeBookFromFavorite with result error`() =
        runTest {
            coEvery {
                removeBookFromFavorite.execute(any())
            } throws Exception()

            val emittedResults = mutableListOf<ViewState<Unit?>>()
            viewModel.bookFavoriteFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())

            viewModel.itemFavoriteBook(Book(id = "1234", isFavorited = true))

            runCurrent()

            coVerify(exactly = 1) { removeBookFromFavorite.execute(any()) }
            coVerify(exactly = 0) { addFavoriteBook.execute(any()) }

            assert(emittedResults.size == 1)
            assert(emittedResults.last() is ViewState.Error)
        }


    @Test
    fun `when itemFavoriteBook() is called and book is not a favorite, should call addFavoriteBook with result success`() =
        runTest {
            coEvery {
                addFavoriteBook.execute(any())
            } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Success(Unit))
            }

            val emittedResults = mutableListOf<ViewState<Unit?>>()
            viewModel.bookFavoriteFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())

            viewModel.itemFavoriteBook(Book(id = "1234", isFavorited = false))

            runCurrent()

            coVerify(exactly = 0) { removeBookFromFavorite.execute(any()) }
            coVerify(exactly = 1) { addFavoriteBook.execute(any()) }

            assert(emittedResults.size == 2)
            assert(emittedResults.first() is ViewState.Loading)
            assert(emittedResults.last() is ViewState.Success)
        }

    @Test
    fun `when itemFavoriteBook() is called and book is not a favorite, should call addFavoriteBook with result error`() =
        runTest {
            coEvery {
                addFavoriteBook.execute(any())
            } throws Exception()

            val emittedResults = mutableListOf<ViewState<Unit?>>()
            viewModel.bookFavoriteFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())
            viewModel.bookFavoriteFlow.onEach {
                emittedResults.add(it)
            }

            viewModel.itemFavoriteBook(Book(id = "1234", isFavorited = false))

            runCurrent()

            coVerify(exactly = 0) { removeBookFromFavorite.execute(any()) }
            coVerify(exactly = 1) { addFavoriteBook.execute(any()) }

            assert(emittedResults.size == 1)
            assert(emittedResults.last() is ViewState.Error)
        }
}