package com.lgdevs.mynextbook.remote.service

import com.google.gson.Gson
import com.lgdevs.mynextbook.common.json.JsonReader
import com.lgdevs.mynextbook.remote.model.BookResponse
import retrofit2.Response

class BookServiceMock: BookService {
    override suspend fun getBooks(
        query: String,
        language: String?,
        filter: String?,
        orderBy: String,
        maxResults: Int
    ): Response<BookResponse> {
        throw Exception()
    }
}