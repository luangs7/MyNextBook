package com.lgdevs.mynextbook.domain.interactor

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetRandomBook
import com.lgdevs.mynextbook.domain.interactor.implementation.GetRandomBookImpl
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.repositories.BookRemoteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

class GetRandomBookImplTest {

    private val repository: BookRemoteRepository = mockk()
    private val useCase: GetRandomBook by lazy { GetRandomBookImpl(repository) }
    private val bookData: Book by lazy { Book(Random.nextInt().toString()) }
    private val bookParam: AppPreferences by lazy { AppPreferences(false, null, false, null) }


    @Test
    fun whenGetRandomBook_passingParams_shouldResponseWithABook() = runTest {
        coEvery { repository.getRandomBook(any()) } returns flow { emit(ApiResult.Success(bookData)) }
        val response = useCase.execute(bookParam).toList()
        assert(response.last() is ApiResult.Success)
    }

    @Test
    fun whenGetRandomBook_andThrowsAnException_shouldResponseWithApiResultError() = runTest {
        coEvery { repository.getRandomBook(any()) } returns flow { emit(ApiResult.Error(Exception())) }
        val response = useCase.execute(bookParam).toList()
        assert(response.last() is ApiResult.Error)
        assert((response.last() as ApiResult.Error).error is Exception)
    }
}