package com.lgdevs.mynextbook.chatia.data.remote.di

import com.lgdevs.mynextbook.chatia.data.remote.datasource.ChatRemoteDataSourceImpl
import com.lgdevs.mynextbook.chatia.data.remote.service.ChatService
import com.lgdevs.mynextbook.chatia.data.remote.service.ChatServiceMock
import com.lgdevs.mynextbook.chatia.data.repository.datasource.ChatRemoteDataSource
import com.lgdevs.mynextbook.common.BuildConfig
import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit

object ChatRemoteModule {
    val modules = module {
        factory<ChatService> { resolveRetrofit() ?: ChatServiceMock() }
        factory<ChatRemoteDataSource> { ChatRemoteDataSourceImpl(get()) }
    }
}

inline fun <reified T> Scope.resolveRetrofit(qualifier: StringQualifier? = null): T? {
    if (BuildConfig.IS_MOCK) return null
    val retrofit = qualifier?.let { get(it) as Retrofit } ?: get() as Retrofit
    return retrofit.create(T::class.java)
}