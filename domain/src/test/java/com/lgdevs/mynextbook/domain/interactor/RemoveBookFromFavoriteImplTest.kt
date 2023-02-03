package com.lgdevs.mynextbook.domain.interactor

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.implementation.RemoveBookFromFavoriteUseCase
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class RemoveBookFromFavoriteImplTest {

    private val repository: BookRepository = mockk()
    private val useCase: RemoveBookFromFavoriteUseCase by lazy { RemoveBookFromFavoriteUseCase(repository::removeFavorite) }
    private val bookParam: Book by lazy { Book(Random.nextInt().toString()) }

    @Test
    fun whenRemoveFavorite_withSuccess_shouldRespondWithApiSuccess() = runTest {
        coEvery { repository.removeFavorite(any()) } returns flow { emit(ApiResult.Success(Unit)) }

        val response = useCase(bookParam).toList()

        assert(response.last() is ApiResult.Success)
    }

    @Test
    fun whenRemoveFavorite_withException_shouldRespondWithApiError() = runTest {
        coEvery { repository.removeFavorite(any()) } returns flow { emit(ApiResult.Error(Exception())) }

        val response = useCase(bookParam).toList()

        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is Exception)
    }
}
