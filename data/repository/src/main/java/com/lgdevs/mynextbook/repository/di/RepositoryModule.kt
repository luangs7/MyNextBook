package com.lgdevs.mynextbook.repository.di

import com.lgdevs.mynextbook.domain.repositories.BookRepository
import com.lgdevs.mynextbook.domain.repositories.PreferencesRepository
import com.lgdevs.mynextbook.domain.repositories.UserDataRepository
import com.lgdevs.mynextbook.repository.implementation.BookRepositoryImpl
import com.lgdevs.mynextbook.repository.implementation.PreferencesRepositoryImpl
import com.lgdevs.mynextbook.repository.implementation.UserDataRepositoryImpl
import com.lgdevs.mynextbook.repository.mapper.BookRepoMapper
import com.lgdevs.mynextbook.repository.mapper.PreferencesRepoMapper
import org.koin.dsl.module

val repositoryModule = module {
    factory<BookRepository> { BookRepositoryImpl(get(), get(), get(), get()) }
    factory<PreferencesRepository> { PreferencesRepositoryImpl(get(), get()) }
    factory<UserDataRepository> { UserDataRepositoryImpl(get(), get()) }
    single { BookRepoMapper() }
    single { PreferencesRepoMapper() }
}