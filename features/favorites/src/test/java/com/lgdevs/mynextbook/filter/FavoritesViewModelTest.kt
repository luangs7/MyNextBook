@file:OptIn(ExperimentalCoroutinesApi::class)

package com.lgdevs.mynextbook.filter

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.implementation.GetFavoriteBooksUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.RemoveBookFromFavoriteUseCase
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.User
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
import kotlin.random.Random

class FavoritesViewModelTest : BaseTest() {
    private val getFavoriteBook: GetFavoriteBooksUseCase = mockk()
    private val removeBookFromFavorite: RemoveBookFromFavoriteUseCase = mockk()
    private val getCurrentUser: GetUserUseCase = mockk()
    private val viewModel: FavoritesViewModel by lazy {
        FavoritesViewModel(
            getFavoriteBook,
            removeBookFromFavorite,
            getCurrentUser,
            testDispatcher
        )
    }

    private val userId = Random.nextInt().toString()
    private val user = User(userId, "Teste", "teste@adbc.com",null)

    @Test
    fun `when getFavoriteItems() is called and the user has favorited books, should return items with success`() = runTest {
        coEvery { getFavoriteBook(userId) } returns flow {
            emit(ApiResult.Loading)
            emit(ApiResult.Success(listOf(Book(id = "1234", isFavorited = true))))
        }

        coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }
        val emittedResults = mutableListOf<ViewState<List<Book>>>()
        viewModel.getFavoriteItems().onEach(emittedResults::add)
            .launchIn(testScheduler.toScope())

        runCurrent()

        coVerify(exactly = 1) { getFavoriteBook(any()) }

        assert(emittedResults.size == 2)
        assert(emittedResults.first() is ViewState.Loading)
        assert(emittedResults.last() is ViewState.Success)
    }

    @Test
    fun `when getFavoriteItems() is called and the user has not favorited books, should return empty`() = runTest {
        coEvery { getFavoriteBook(userId) } returns flow {
            emit(ApiResult.Loading)
            emit(ApiResult.Empty)
        }
        coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

        val emittedResults = mutableListOf<ViewState<List<Book>>>()
        viewModel.getFavoriteItems().onEach(emittedResults::add)
            .launchIn(testScheduler.toScope())

        runCurrent()

        coVerify(exactly = 1) { getFavoriteBook(any()) }

        assert(emittedResults.size == 2)
        assert(emittedResults.first() is ViewState.Loading)
        assert(emittedResults.last() is ViewState.Empty)
    }

    @Test
    fun `when getFavoriteItems() is called and an exception occurs, should return error`() = runTest {
        coEvery { getFavoriteBook(userId) } throws Exception()
        coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

        val emittedResults = viewModel.getFavoriteItems().toList()
        runCurrent()

        coVerify(exactly = 1) { getFavoriteBook(any()) }

        assert(emittedResults.size == 1)
        assert(emittedResults.last() is ViewState.Error)
    }

    @Test
    fun `when removeItem() is called and book is favorite, should call removeBookFromFavorite with result success`() =
        runTest {
            coEvery {
                removeBookFromFavorite(any())
            } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Success(Unit))
            }

            val emittedResults = mutableListOf<ApiResult<Unit?>>()
            viewModel.removeItem(Book(id = "1234", isFavorited = true)).onEach(emittedResults::add)
                .launchIn(testScheduler.toScope())

            runCurrent()

            coVerify(exactly = 1) { removeBookFromFavorite(any()) }

            assert(emittedResults.size == 2)
            assert(emittedResults.first() is ApiResult.Loading)
            assert(emittedResults.last() is ApiResult.Success)
        }
}