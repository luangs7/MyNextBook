package com.lgdevs.mynextbook.remote.di

import com.lgdevs.mynextbook.remote.BuildConfig
import com.lgdevs.mynextbook.remote.interceptor.QueryFormatInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single { provideOkHttpClient() }

    single { provideRetrofit(get()) }
}

private fun provideOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .followRedirects(true)
        .addNetworkInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            },
        )
        .addInterceptor(QueryFormatInterceptor())
    return builder.build()
}

private fun provideRetrofit(
    client: OkHttpClient,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
