package com.lgdevs.mynextbook.repository.datasource

import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo
import kotlinx.coroutines.flow.Flow

interface BookDataSourceRemote {
    suspend fun getBooksFromQuery(appPreferencesRepo: AppPreferencesRepo): Flow<BookData>
}