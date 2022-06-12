package com.lgdevs.mynextbook.remote.service

import com.lgdevs.mynextbook.remote.datasource.BookDataSourceRemoteImpl
import com.lgdevs.mynextbook.remote.model.BookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("volumes")
    suspend fun getBooks(
        @Query(BookDataSourceRemoteImpl.QUERY, encoded = false) query: String,
        @Query(BookDataSourceRemoteImpl.LANGUAGE_RESTRICT_QUERY) language: String?,
        @Query(BookDataSourceRemoteImpl.FILTER_QUERY) filter: String?,
        @Query(BookDataSourceRemoteImpl.ORDERBY_QUERY) orderBy: String = BookDataSourceRemoteImpl.ORDERBY_QUERY_VALUE,
        @Query(BookDataSourceRemoteImpl.MAXRESULTS_QUERY) maxResults: Int = BookDataSourceRemoteImpl.MAXRESULTS_VALUE,
    ): Response<BookResponse>
}