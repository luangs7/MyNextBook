package com.lgdevs.mynextbook.remote.service

import com.google.gson.Gson
import com.lgdevs.mynextbook.common.json.JsonReader
import com.lgdevs.mynextbook.remote.model.BookResponse
import retrofit2.Response

class BookServiceMock: BookService {
    override suspend fun getBooks(
        query: String,
        language: String?,
        orderBy: String
    ): Response<BookResponse> {
        val response = JsonReader.readMockedJson("BookResponse.json")
        return Response.success(Gson().fromJson(response, BookResponse::class.java))
    }
}