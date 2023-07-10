package com.lgdevs.mynextbook.repository.datasource

import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo
import com.lgdevs.mynextbook.repository.model.BookData
import kotlinx.coroutines.flow.Flow

interface BookDataSourceRemote {
    fun getBooksFromQuery(appPreferencesRepo: AppPreferencesRepo): Flow<BookData>
}
