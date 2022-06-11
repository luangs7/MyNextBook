package com.lgdevs.local.di

import android.content.Context
import androidx.room.Room
import com.lgdevs.local.dao.BookDao
import com.lgdevs.local.dao.BookDatabase
import com.lgdevs.local.datasource.BookDataSourceLocalImpl
import com.lgdevs.local.mapper.BookEntityMapper
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceLocal
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun provideDatabase(application: Context): BookDatabase {
    return Room.databaseBuilder(
        application,
        BookDatabase::class.java,
        BookDatabase::class.java.name
    )
        .fallbackToDestructiveMigration()
        .build()
}

fun provideDao(database: BookDatabase): BookDao {
    return database.dao
}

val localModule = module {
    factory<BookDataSourceLocal> { BookDataSourceLocalImpl(get(), get()) }
    single { BookEntityMapper() }
    single { provideDatabase(androidContext()) }
    single { provideDao(get()) }
}

