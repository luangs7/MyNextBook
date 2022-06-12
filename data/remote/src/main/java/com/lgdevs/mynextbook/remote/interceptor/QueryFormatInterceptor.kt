package com.lgdevs.mynextbook.remote.interceptor

import com.lgdevs.mynextbook.remote.datasource.BookDataSourceRemoteImpl
import okhttp3.Interceptor
import okhttp3.Response

class QueryFormatInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.url.query?.contains("${BookDataSourceRemoteImpl.QUERY}=${BookDataSourceRemoteImpl.QUERY_DIVIDER}") == true) {
            val newUrl = request.url.toString().replace(
                "${BookDataSourceRemoteImpl.QUERY}=${BookDataSourceRemoteImpl.QUERY_DIVIDER}",
                "${BookDataSourceRemoteImpl.QUERY}="
            )
            request = request.newBuilder()
                .url(newUrl)
                .build()
        }

        return chain.proceed(request)
    }
}