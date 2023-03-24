package com.lgdevs.mynextbook.finder

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.common.dispatcher.CoroutineDispatcherProvider
import com.lgdevs.mynextbook.domain.interactor.implementation.AddFavoriteBookUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetPreferencesUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetRandomBookUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.RemoveBookFromFavoriteUseCase
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.User
import com.lgdevs.mynextbook.finder.preview.viewmodel.PreviewViewModel
import com.lgdevs.mynextbook.tests.BaseTest
import com.lgdevs.mynextbook.tests.toScope
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class PreviewViewModelTest : BaseTest() {

    private val getPreferences: GetPreferencesUseCase = mockk()
    private val getRandomBook: GetRandomBookUseCase = mockk()
    private val addFavoriteBook: AddFavoriteBookUseCase = mockk()
    private val removeBookFromFavorite: RemoveBookFromFavoriteUseCase = mockk()
    private val getCurrentUser: GetUserUseCase = mockk()
    private val userId = Random.nextInt().toString()
    private val user = User(userId, "Teste", "teste@adbc.com", null)
    private val coroutinesProvider = mockk<CoroutineDispatcherProvider>()

    private val viewModel: PreviewViewModel by lazy {
        PreviewViewModel(
            getPreferences,
            getRandomBook,
            addFavoriteBook,
            removeBookFromFavorite,
            getCurrentUser,
            coroutinesProvider,
        )
    }

    private val appPreferences = AppPreferences(
        isEbook = false,
        isPortuguese = false,
        keyword = String(),
        subject = String(),
    )

    private val commonValidation = {
        coVerify(exactly = 1) {
            getPreferences(userId)
            getRandomBook(any())
        }
    }

    @Before
    fun before() {
        every { coroutinesProvider.invoke() } returns testDispatcher
    }

    @Test
    fun `when getRandomBook() is called and data is valid, should return loading and success status and item id should be valid`() =
        runTest {
            coEvery { getPreferences(any()) } returns flow {
                emit(appPreferences)
            }
            coEvery { getRandomBook(any()) } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Success(Book(id = "1234")))
            }
            coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

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
            coEvery { getPreferences(any()) } returns flow {
                emit(appPreferences)
            }
            coEvery { getRandomBook(any()) } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Empty)
            }
            coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

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
            coEvery { getPreferences(any()) } returns flow {
                emit(appPreferences)
            }
            coEvery { getRandomBook(any()) } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Error(Exception()))
            }
            coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

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
        coEvery { getPreferences(any()) } returns flow {
            emit(appPreferences)
        }
        coEvery { getRandomBook(any()) } throws Exception()
        coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

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
                removeBookFromFavorite(any())
            } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Success(Unit))
            }
            coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

            val emittedResults = mutableListOf<ViewState<Unit?>>()
            viewModel.bookFavoriteFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())

            viewModel.itemFavoriteBook(Book(id = "1234", isFavorited = true))

            runCurrent()

            coVerify(exactly = 1) { removeBookFromFavorite(any()) }
            coVerify(exactly = 0) { addFavoriteBook(any(), any()) }

            assert(emittedResults.size == 2)
            assert(emittedResults.first() is ViewState.Loading)
            assert(emittedResults.last() is ViewState.Success)
        }

    @Test
    fun `when itemFavoriteBook() is called and book is favorite, should call removeBookFromFavorite with result error`() =
        runTest {
            coEvery {
                removeBookFromFavorite(any())
            } throws Exception()
            coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

            val emittedResults = mutableListOf<ViewState<Unit?>>()
            viewModel.bookFavoriteFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())

            viewModel.itemFavoriteBook(Book(id = "1234", isFavorited = true))

            runCurrent()

            coVerify(exactly = 1) { removeBookFromFavorite(any()) }
            coVerify(exactly = 0) { addFavoriteBook(any(), any()) }

            assert(emittedResults.size == 1)
            assert(emittedResults.last() is ViewState.Error)
        }

    @Test
    fun `when itemFavoriteBook() is called and book is not a favorite, should call addFavoriteBook with result success`() =
        runTest {
            coEvery {
                addFavoriteBook(any(), any())
            } returns flow {
                emit(ApiResult.Loading)
                emit(ApiResult.Success(Unit))
            }
            coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

            val emittedResults = mutableListOf<ViewState<Unit?>>()
            viewModel.bookFavoriteFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())

            viewModel.itemFavoriteBook(Book(id = "1234", isFavorited = false))

            runCurrent()

            coVerify(exactly = 0) { removeBookFromFavorite(any()) }
            coVerify(exactly = 1) { addFavoriteBook(any(), any()) }

            assert(emittedResults.size == 2)
            assert(emittedResults.first() is ViewState.Loading)
            assert(emittedResults.last() is ViewState.Success)
        }

    @Test
    fun `when itemFavoriteBook() is called and book is not a favorite, should call addFavoriteBook with result error`() =
        runTest {
            coEvery {
                addFavoriteBook(any(), any())
            } throws Exception()
            coEvery { getCurrentUser() } returns flow { emit(ApiResult.Success(user)) }

            val emittedResults = mutableListOf<ViewState<Unit?>>()
            viewModel.bookFavoriteFlow.onEach(emittedResults::add).launchIn(testScheduler.toScope())
            viewModel.bookFavoriteFlow.onEach {
                emittedResults.add(it)
            }

            viewModel.itemFavoriteBook(Book(id = "1234", isFavorited = false))

            runCurrent()

            coVerify(exactly = 0) { removeBookFromFavorite(any()) }
            coVerify(exactly = 1) { addFavoriteBook(any(), any()) }

            assert(emittedResults.size == 1)
            assert(emittedResults.last() is ViewState.Error)
        }
}
