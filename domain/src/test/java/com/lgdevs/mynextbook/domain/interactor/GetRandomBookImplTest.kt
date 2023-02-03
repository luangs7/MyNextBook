package com.lgdevs.mynextbook.domain.interactor

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.implementation.GetRandomBookUseCase
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.repositories.BookRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class GetRandomBookImplTest {

    private val repository: BookRepository = mockk()
    private val useCase: GetRandomBookUseCase by lazy { GetRandomBookUseCase(repository::getRandomBook) }
    private val bookData: Book by lazy { Book(Random.nextInt().toString()) }
    private val bookParam: AppPreferences by lazy { AppPreferences(false, null, false, null) }


    @Test
    fun whenGetRandomBook_passingParams_shouldResponseWithABook() = runTest {
        coEvery { repository.getRandomBook(any()) } returns flow { emit(ApiResult.Success(bookData)) }
        val response = useCase(bookParam).toList()
        assert(response.last() is ApiResult.Success)
    }

    @Test
    fun whenGetRandomBook_andThrowsAnException_shouldResponseWithApiResultError() = runTest {
        coEvery { repository.getRandomBook(any()) } returns flow { emit(ApiResult.Error(Exception())) }
        val response = useCase(bookParam).toList()
        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is Exception)
    }
}