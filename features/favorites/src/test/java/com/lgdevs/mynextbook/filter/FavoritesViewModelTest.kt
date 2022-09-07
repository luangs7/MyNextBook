@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lgdevs.mynextbook.filter

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.abstraction.AddFavoriteBook
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetFavoriteBooks
import com.lgdevs.mynextbook.domain.interactor.abstraction.RemoveBookFromFavorite
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.favorites.FavoritesViewModel
import com.lgdevs.mynextbook.tests.BaseTest
import com.lgdevs.mynextbook.tests.toScope
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test

class FavoritesViewModelTest : BaseTest() {
    private val getFavoriteBook: GetFavoriteBooks = mockk()
    private val removeBookFromFavorite: RemoveBookFromFavorite = mockk()
    private val viewModel: FavoritesViewModel by lazy {
        FavoritesViewModel(
            getFavoriteBook,
            removeBookFromFavorite,
            testDispatcher
        )
    }

    @Test
    fun `when getFavoriteItems() is called and the user has favorited books, should return items with success`() = runTest {
        coEvery { getFavoriteBook.execute(Unit) } returns flow {
            emit(ApiResult.Loading)
            emit(ApiResult.Success(listOf(Book(id = "1234", isFavorited = true))))
        }

        val emittedResults = mutableListOf<ViewState<List<Book>>>()
        viewModel.getFavoriteItems().onEach(emittedResults::add)
            .launchIn(testScheduler.toScope())

        runCurrent()

        coVerify(exactly = 1) { getFavoriteBook.execute(Unit) }

        assert(emittedResults.size == 2)
        assert(emittedResults.first() is ViewState.Loading)
        assert(emittedResults.last() is ViewState.Success)
    }

    @Test
    fun `when getFavoriteItems() is called and the user has not favorited books, should return empty`() = runTest {
        coEvery { getFavoriteBook.execute(Unit) } returns flow {
            emit(ApiResult.Loading)
            emit(ApiResult.Empty)
        }

        val emittedResults = mutableListOf<ViewState<List<Book>>>()
        viewModel.getFavoriteItems().onEach(emittedResults::add)
            .launchIn(testScheduler.toScope())

        runCurrent()

        coVerify(exactly = 1) { getFavoriteBook.execute(Unit) }

        assert(emittedResults.size == 2)
        assert(emittedResults.first() is ViewState.Loading)
        assert(emittedResults.last() is ViewState.Empty)
    }

    @Test
    fun `when getFavoriteItems() is called and an exception occurs, should return error`() = runTest {
        coEvery { getFavoriteBook.execute(Unit) } throws Exception()

        val emittedResults = viewModel.getFavoriteItems().toList()
        runCurrent()

        coVerify(exactly = 1) { getFavoriteBook.execute(Unit) }

        assert(emittedResults.size == 1)
        assert(emittedResults.last() is ViewState.Error)
    }

    @Test
    fun `when removeItem() is called and book is favorite, should call removeBookFromFavorite with result success`() =
        runTest {
            coEvery {
                removeBookFromFavorite.execute(any())
            } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Success(Unit))
            }

            val emittedResults = mutableListOf<ApiResult<Unit?>>()
            viewModel.removeItem(Book(id = "1234", isFavorited = true)).onEach(emittedResults::add)
                .launchIn(testScheduler.toScope())

            runCurrent()

            coVerify(exactly = 1) { removeBookFromFavorite.execute(any()) }

            assert(emittedResults.size == 2)
            assert(emittedResults.first() is ApiResult.Loading)
            assert(emittedResults.last() is ApiResult.Success)
        }
}