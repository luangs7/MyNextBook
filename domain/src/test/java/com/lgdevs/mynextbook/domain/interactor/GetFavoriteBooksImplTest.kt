package com.lgdevs.mynextbook.domain.interactor

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.abstraction.AddFavoriteBook
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetFavoriteBooks
import com.lgdevs.mynextbook.domain.interactor.implementation.AddFavoriteBookImpl
import com.lgdevs.mynextbook.domain.interactor.implementation.GetFavoriteBooksImpl
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookLocalRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class GetFavoriteBooksImplTest {

    private val repository: BookLocalRepository = mockk()
    private val useCase: GetFavoriteBooks by lazy { GetFavoriteBooksImpl(repository) }
    private val bookDataList: List<Book> by lazy {
        listOf(
            Book(Random.nextInt().toString()),
            Book(Random.nextInt().toString()),
            Book(Random.nextInt().toString())
        )
    }

    @Test
    fun whenGetFavorites_withItemsOnList_shouldRespondWithSuccess_andItemsAsDomainObject() =
        runTest {
            coEvery { repository.getFavorites() } returns flow { emit(ApiResult.Success(bookDataList)) }
            val response = useCase.execute(Unit).toList()
            assert(response.last() is ApiResult.Success)
            assert((response.last() as ApiResult.Success).data?.size == bookDataList.size)
            assert((response.last() as ApiResult.Success).data?.first() is Book)
        }

    @Test
    fun whenGetFavorites_withoutItemsOnList_shouldRespondWithEmpty() =
        runTest {
            coEvery { repository.getFavorites() } returns flow { emit(ApiResult.Empty) }
            val response = useCase.execute(Unit).toList()
            assert(response.last() is ApiResult.Empty)
        }

    @Test
    fun whenGetFavorites_withException_shouldRespondWithError() =
        runTest {
            coEvery { repository.getFavorites() } returns flow { emit(ApiResult.Error(Exception())) }
            val response = useCase.execute(Unit).toList()
            assert(response.last() is ApiResult.Error)
            assert((response.last() as ApiResult.Error).error is Exception)
        }

}