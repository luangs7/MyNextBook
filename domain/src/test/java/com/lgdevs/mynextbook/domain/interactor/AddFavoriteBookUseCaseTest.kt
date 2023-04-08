package com.lgdevs.mynextbook.domain.interactor

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.implementation.AddFavoriteBookUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.favoriteBookFactory
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.User
import com.lgdevs.mynextbook.domain.repositories.BookRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.security.InvalidParameterException
import kotlin.random.Random

class AddFavoriteBookUseCaseTest {

    private val repository: BookRepository = mockk()
    private val useCase: AddFavoriteBookUseCase by lazy { AddFavoriteBookUseCase { book, user ->
            favoriteBookFactory(book, user, repository)
        }
    }
    private val bookParam: Book by lazy { Book(Random.nextInt().toString()) }
    private val user = User(String(),"Test User", String(), null)
    @Test
    fun whenAddFavorite_withSuccess_shouldRespondWithApiSuccess() = runTest {
        coEvery { repository.addFavorites(any(), any()) } returns flow { emit(ApiResult.Success(Unit)) }

        val response = useCase(bookParam, user).toList()

        assert(response.last() is ApiResult.Success)
    }

    @Test
    fun whenAddFavorite_withException_shouldRespondWithApiError() = runTest {
        coEvery { repository.addFavorites(any(), any()) } returns flow { emit(ApiResult.Error(Exception())) }

        val response = useCase(bookParam, user).toList()

        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is Exception)
    }

    @Test
    fun whenAddFavorite_withoutUser_shouldThrowsException() = runTest {
        val response = useCase(bookParam, null).toList()
        coVerify(exactly = 0) { repository.addFavorites(any(), any()) }
        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is InvalidParameterException)
    }
}