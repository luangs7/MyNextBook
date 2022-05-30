package com.lgdevs.mynextbook.repository.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookLocalRepository
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceLocal
import com.lgdevs.mynextbook.repository.model.BookData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import kotlin.random.Random

class BookLocalRepositoryImplTest {

    private val dataSourceLocal = mockk<BookDataSourceLocal>()
    private val repository: BookLocalRepository by lazy { BookLocalRepositoryImpl(dataSourceLocal) }
    private val bookParam: Book by lazy { Book(Random.nextInt().toString()) }
    private val bookDataList: List<BookData> by lazy {
        listOf(
            BookData(Random.nextInt().toString()),
            BookData(Random.nextInt().toString()),
            BookData(Random.nextInt().toString())
        )
    }

    @Test
    fun whenAddFavorite_withSuccess_shouldRespondWithApiSuccess() = runTest {
        coEvery { dataSourceLocal.setFavoriteBook(any()) } returns flow { emit(Unit) }

        val response = repository.addFavorites(bookParam).toList()

        assert(response.first() is ApiResult.Loading)
        assert(response.last() is ApiResult.Success)
    }

    @Test
    fun whenAddFavorite_withException_shouldRespondWithApiError() = runTest {
        coEvery { dataSourceLocal.setFavoriteBook(any()) } returns flow { throw Exception() }

        val response = repository.addFavorites(bookParam).toList()

        assert(response.first() is ApiResult.Loading)
        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is Exception)
    }

    @Test
    fun whenGetFavorites_withItemsOnList_shouldRespondWithSuccess_andItemsAsDomainObject() =
        runTest {
            coEvery { dataSourceLocal.getFavoritesBooks() } returns flow { emit(bookDataList) }
            val response = repository.getFavorites().toList()
            assert(response.first() is ApiResult.Loading)
            assert(response.last() is ApiResult.Success)
            assert((response.last() as ApiResult.Success).data?.size == bookDataList.size)
            assert((response.last() as ApiResult.Success).data?.first() is Book)
        }

    @Test
    fun whenGetFavorites_withoutItemsOnList_shouldRespondWithEmpty() =
        runTest {
            coEvery { dataSourceLocal.getFavoritesBooks() } returns flow { emit(listOf()) }
            val response = repository.getFavorites().toList()
            assert(response.first() is ApiResult.Loading)
            assert(response.last() is ApiResult.Empty)
        }

    @Test
    fun whenGetFavorites_withException_shouldRespondWithError() =
        runTest {
            coEvery { dataSourceLocal.getFavoritesBooks() } returns flow { throw Exception() }
            val response = repository.getFavorites().toList()
            assert(response.first() is ApiResult.Loading)
            assert(response.last() is ApiResult.Error)
            assert((response.last() as ApiResult.Error).error is Exception)
        }


    @Test
    fun whenRemoveFavorite_withSuccess_shouldRespondWithApiSuccess() = runTest {
        coEvery { dataSourceLocal.removeFavoriteBook(any()) } returns flow { emit(Unit) }

        val response = repository.removeFavorite(bookParam).toList()

        assert(response.first() is ApiResult.Loading)
        assert(response.last() is ApiResult.Success)
    }

    @Test
    fun whenRemoveFavorite_withException_shouldRespondWithApiError() = runTest {
        coEvery { dataSourceLocal.removeFavoriteBook(any()) } returns flow { throw Exception() }

        val response = repository.removeFavorite(bookParam).toList()

        assert(response.first() is ApiResult.Loading)
        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is Exception)
    }
}