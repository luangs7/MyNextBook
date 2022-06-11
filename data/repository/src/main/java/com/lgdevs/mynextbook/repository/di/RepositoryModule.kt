package com.lgdevs.mynextbook.repository.di

import com.lgdevs.mynextbook.domain.repositories.BookLocalRepository
import com.lgdevs.mynextbook.domain.repositories.BookRemoteRepository
import com.lgdevs.mynextbook.domain.repositories.PreferencesRepository
import com.lgdevs.mynextbook.repository.datasource.PreferencesDataSourceDatastore
import com.lgdevs.mynextbook.repository.implementation.BookLocalRepositoryImpl
import com.lgdevs.mynextbook.repository.implementation.BookRemoteRepositoryImpl
import com.lgdevs.mynextbook.repository.implementation.PreferencesRepositoryImpl
import com.lgdevs.mynextbook.repository.mapper.BookRepoMapper
import com.lgdevs.mynextbook.repository.mapper.PreferencesRepoMapper
import org.koin.dsl.module

val repositoryModule = module {
    factory<BookLocalRepository> { BookLocalRepositoryImpl(get(), get()) }
    factory<BookRemoteRepository> { BookRemoteRepositoryImpl(get(), get(), get()) }
    factory<PreferencesRepository> { PreferencesRepositoryImpl(get(), get()) }
    single { BookRepoMapper() }
    single { PreferencesRepoMapper() }
}