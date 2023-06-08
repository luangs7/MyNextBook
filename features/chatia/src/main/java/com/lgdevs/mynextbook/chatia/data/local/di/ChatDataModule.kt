package com.lgdevs.mynextbook.chatia.data.local.di

import android.content.Context
import androidx.room.Room
import com.lgdevs.mynextbook.chatia.data.local.dao.ChatDao
import com.lgdevs.mynextbook.chatia.data.local.dao.ChatDatabase
import com.lgdevs.mynextbook.chatia.data.local.datasource.ChatLocalDataSourceImpl
import com.lgdevs.mynextbook.chatia.data.local.mapper.MessageEntityMapper
import com.lgdevs.mynextbook.chatia.data.repository.datasource.ChatLocalDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object ChatDataModule {
    var modules = module {
        single { MessageEntityMapper() }
        single { provideDatabase(androidContext()) }
        single { provideDao(get()) }
        factory<ChatLocalDataSource> { ChatLocalDataSourceImpl(get(), get()) }
    }

    private fun provideDatabase(application: Context): ChatDatabase {
        return Room.databaseBuilder(
            application,
            ChatDatabase::class.java,
            ChatDatabase::class.java.name,
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun provideDao(database: ChatDatabase): ChatDao {
        return database.dao
    }
}
