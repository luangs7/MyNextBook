package com.lgdevs.mynextbook.remote.service

import com.lgdevs.mynextbook.remote.model.BookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query:String,
        @Query("langRestrict") language:String?,
        @Query("orderBy") orderBy:String = "relevance"
    ): Response<BookResponse>
}