package com.lgdevs.mynextbook.remote.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.common.json.JsonReader
import com.lgdevs.mynextbook.remote.model.BookResponse
import com.lgdevs.mynextbook.remote.service.BookService
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceRemote
import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.BookParams
import com.lgdevs.mynextbook.tests.CoroutineTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response
import java.security.InvalidParameterException

class BookDataSourceRemoteImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private val service = mockk<BookService>()
    private val bookDataSourceRemote: BookDataSourceRemote by lazy {
        BookDataSourceRemoteImpl(
            service
        )
    }

    @Test
    fun whenGetBookList_withSuccessfulCall_shouldReceiveARandomItem_asRepoItem() = runTest {
        val apiCall = Gson().fromJson(
            JsonReader.readMockedJson("BookResponse.json"),
            BookResponse::class.java
        )
        coEvery { service.getBooks(any(), any(), any()) } returns Response.success(apiCall)

        val params = BookParams(false, String(), String(), String())

        bookDataSourceRemote.getBooksFromQuery(params).collect { Assert.assertNotNull(it) }

        coVerify(exactly = 1) {
            service.getBooks(any(), any(), any())
        }
    }

    @Test
    fun whenGetBookList_withSuccessfulCall_andBodyNull_shouldShowError() = runTest {
        coEvery { service.getBooks(any(), any(), any()) } returns Response.success(null)

        val params = BookParams(false, String(), String(), String())
        bookDataSourceRemote.getBooksFromQuery(params)
            .catch { assert(it is Exception) }
            .collect { Assert.assertNull(it) }

        coVerify(exactly = 1) {
            service.getBooks(any(), any(), any())
        }
    }


    @Test
    fun whenGetBookList_shouldAssertIsFailureCall() = runTest {
        coEvery { service.getBooks(any(), any(), any()) } returns Response.error(
            500,
            String().toResponseBody(null)
        )

        val params = BookParams(false, String(), String(), String())

        bookDataSourceRemote.getBooksFromQuery(params)
            .catch { assert(it is Exception) }
            .collect { Assert.assertNull(it) }

        coVerify(exactly = 1) {
            service.getBooks(any(), any(), any())
        }

    }
}