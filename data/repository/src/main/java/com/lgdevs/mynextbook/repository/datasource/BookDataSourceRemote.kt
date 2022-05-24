package com.lgdevs.mynextbook.repository.datasource

import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.BookParams
import kotlinx.coroutines.flow.Flow

interface BookDataSourceRemote {
    suspend fun getBooksFromQuery(bookParams: BookParams): Flow<BookData>
}