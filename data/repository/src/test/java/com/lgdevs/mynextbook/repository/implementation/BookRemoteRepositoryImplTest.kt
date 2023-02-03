package com.lgdevs.mynextbook.repository.implementation

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.model.AppPreferences
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
    private val bookMapper: BookRepoMapper by lazy { BookRepoMapper() }
    private val prefMapper: PreferencesRepoMapper by lazy { PreferencesRepoMapper() }
    private val repository: BookRepository by lazy { BookRepositoryImpl(dataSourceRemote, prefMapper, bookMapper) }
    private val bookData: BookData by lazy { BookData(Random.nextInt().toString()) }
    private val bookParam: AppPreferences by lazy { AppPreferences(false, null, false, null) }

    @Test
    fun whenGetRandomBook_passingParams_shouldResponseWithABook() = runTest {
        coEvery { dataSourceRemote.getBooksFromQuery(any()) } returns flow { emit(bookData) }
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
}