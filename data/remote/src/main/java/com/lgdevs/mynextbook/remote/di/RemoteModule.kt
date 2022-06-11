package com.lgdevs.mynextbook.remote.di

import com.lgdevs.mynextbook.remote.datasource.BookDataSourceRemoteImpl
import com.lgdevs.mynextbook.remote.mapper.BookRemoteMapper
import com.lgdevs.mynextbook.remote.service.BookService
import com.lgdevs.mynextbook.remote.service.BookServiceMock
import com.lgdevs.mynextbook.remote.util.resolveRetrofit
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceRemote
import org.koin.dsl.module

val remoteModule = module {
    factory<BookService> { resolveRetrofit() ?: BookServiceMock() }
    factory<BookDataSourceRemote> { BookDataSourceRemoteImpl(get(), get()) }
    single { BookRemoteMapper() }
}