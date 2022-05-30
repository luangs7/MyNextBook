package com.lgdevs.mynextbook.remote.service

import com.lgdevs.mynextbook.remote.model.BookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
//    https://www.googleapis.com/books/v1/volumes?q=subject:fiction&langRestrict=pt&maxResults=40&filter=ebooks
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query:String,
        @Query("langRestrict") language:String?,
        @Query("filter") filter:String?,
        @Query("orderBy") orderBy:String = "relevance",
        @Query("maxResults") maxResults:Int = 40,
    ): Response<BookResponse>
}