package com.lgdevs.mynextbook.repository.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookRepository
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceLocal
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceRemote
import com.lgdevs.mynextbook.repository.mapper.BookRepoMapper
import com.lgdevs.mynextbook.repository.mapper.PreferencesRepoMapper
import com.lgdevs.mynextbook.repository.model.BookData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class BookRepositoryImplTest {

    private val dataSourceRemote = mockk<BookDataSourceRemote>()
    private val dataSourceLocal = mockk<BookDataSourceLocal>()
    private val bookMapper: BookRepoMapper by lazy { BookRepoMapper() }
    private val prefMapper: PreferencesRepoMapper by lazy { PreferencesRepoMapper() }
    private val repository: BookRepository by lazy { BookRepositoryImpl(dataSourceLocal, bookMapper, dataSourceRemote, prefMapper, ) }
    private val bookData: BookData by lazy { BookData(Random.nextInt().toString()) }
    private val bookParam: AppPreferences by lazy { AppPreferences(false, null, false, null) }
    private val bookMock: Book by lazy { Book(Random.nextInt().toString()) }
    private val bookDataList: List<BookData> by lazy {
        listOf(
            BookData(Random.nextInt().toString()),
            BookData(Random.nextInt().toString()),
            BookData(Random.nextInt().toString())
        )
    }
    private val userId = Random.nextInt(99).toString()

    @Test
    fun whenGetRandomBook_passingParams_shouldResponseWithABook() = runTest {
        coEvery { dataSourceRemote.getBooksFromQuery(any()) } returns flow { emit(bookData) }
        coEvery { dataSourceLocal.getFavoriteBook(any()) } returns flow { emit(bookData) }
        val response = repository.getRandomBook(bookParam).toList()
        assert(response.first() is ApiResult.Loading)
        assert(response.last() is ApiResult.Success)
    }

    @Test
    fun whenGetRandomBook_andThrowsAnException_shouldResponseWithApiResultError() = runTest {
        coEvery { dataSourceRemote.getBooksFromQuery(any()) } returns flow { throw Exception() }
        val response = repository.getRandomBook(bookParam).toList()
        assert(response.first() is ApiResult.Loading)
        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is Exception)
    }

    @Test
    fun whenAddFavorite_withSuccess_shouldRespondWithApiSuccess() = runTest {
        coEvery { dataSourceLocal.setFavoriteBook(any(), any()) } returns flow { emit(Unit) }

        val response = repository.addFavorites(bookMock, userId).toList()

        assert(response.first() is ApiResult.Loading)
        assert(response.last() is ApiResult.Success)
    }

    @Test
    fun whenAddFavorite_withException_shouldRespondWithApiError() = runTest {
        coEvery { dataSourceLocal.setFavoriteBook(any(), any()) } returns flow { throw Exception() }

        val response = repository.addFavorites(bookMock, userId).toList()

        assert(response.first() is ApiResult.Loading)
        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is Exception)
    }

    @Test
    fun whenGetFavorites_withItemsOnList_shouldRespondWithSuccess_andItemsAsDomainObject() =
        runTest {
            coEvery { dataSourceLocal.getFavoritesBooks(any()) } returns flow { emit(bookDataList) }
            val response = repository.getFavorites(userId).toList()
            assert(response.first() is ApiResult.Loading)
            assert(response.last() is ApiResult.Success)
            assert((response.last() as ApiResult.Success).data?.size == bookDataList.size)
            assert((response.last() as ApiResult.Success).data?.first() is Book)
        }

    @Test
    fun whenGetFavorites_withoutItemsOnList_shouldRespondWithEmpty() =
        runTest {
            coEvery { dataSourceLocal.getFavoritesBooks(any()) } returns flow { emit(listOf()) }
            val response = repository.getFavorites(userId).toList()
            assert(response.first() is ApiResult.Loading)
            assert(response.last() is ApiResult.Empty)
        }

    @Test
    fun whenGetFavorites_withException_shouldRespondWithError() =
        runTest {
            coEvery { dataSourceLocal.getFavoritesBooks(any()) } returns flow { throw Exception() }
            val response = repository.getFavorites(userId).toList()
            assert(response.first() is ApiResult.Loading)
            assert(response.last() is ApiResult.Error)
            assert((response.last() as ApiResult.Error).error is Exception)
        }


    @Test
    fun whenRemoveFavorite_withSuccess_shouldRespondWithApiSuccess() = runTest {
        coEvery { dataSourceLocal.removeFavoriteBook(any()) } returns flow { emit(Unit) }

        val response = repository.removeFavorite(bookMock).toList()

        assert(response.first() is ApiResult.Loading)
        assert(response.last() is ApiResult.Success)
    }

    @Test
    fun whenRemoveFavorite_withException_shouldRespondWithApiError() = runTest {
        coEvery { dataSourceLocal.removeFavoriteBook(any()) } returns flow { throw Exception() }

        val response = repository.removeFavorite(bookMock).toList()

        assert(response.first() is ApiResult.Loading)
        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is Exception)
    }
}