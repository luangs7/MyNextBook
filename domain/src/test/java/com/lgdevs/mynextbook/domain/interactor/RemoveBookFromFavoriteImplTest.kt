package com.lgdevs.mynextbook.domain.interactor

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.abstraction.AddFavoriteBook
import com.lgdevs.mynextbook.domain.interactor.abstraction.RemoveBookFromFavorite
import com.lgdevs.mynextbook.domain.interactor.implementation.AddFavoriteBookImpl
import com.lgdevs.mynextbook.domain.interactor.implementation.RemoveBookFromFavoriteImpl
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookLocalRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class RemoveBookFromFavoriteImplTest {

    private val repository: BookLocalRepository = mockk()
    private val useCase: RemoveBookFromFavorite by lazy { RemoveBookFromFavoriteImpl(repository) }
    private val bookParam: Book by lazy { Book(Random.nextInt().toString()) }

    @Test
    fun whenRemoveFavorite_withSuccess_shouldRespondWithApiSuccess() = runTest {
        coEvery { repository.removeFavorite(any()) } returns flow { emit(ApiResult.Success(Unit)) }

        val response = useCase.execute(bookParam).toList()

        assert(response.last() is ApiResult.Success)
    }

    @Test
    fun whenRemoveFavorite_withException_shouldRespondWithApiError() = runTest {
        coEvery { repository.removeFavorite(any()) } returns flow { emit(ApiResult.Error(Exception())) }

        val response = useCase.execute(bookParam).toList()

        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is Exception)
    }
}
