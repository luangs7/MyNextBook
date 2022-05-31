package com.lgdevs.mynextbook.remote.datasource

import com.lgdevs.mynextbook.remote.mapper.BookRemoteMapper
import com.lgdevs.mynextbook.remote.service.BookService
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceRemote
import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class BookDataSourceRemoteImpl(
    private val service: BookService,
    private val mapper: BookRemoteMapper
) : BookDataSourceRemote {
    override suspend fun getBooksFromQuery(appPreferencesRepo: AppPreferencesRepo): Flow<BookData> = flow {
        val filter = if (appPreferencesRepo.isEbook) EBOOK_QUERY else null
        val query = appPreferencesRepo.keyword?.replace(DIVIDER_OLD, DIVIDER) ?: String()
        val response = service.getBooks(query, appPreferencesRepo.language, filter)
        if (response.isSuccessful) {
            response.body()?.let {
                val item = mapper.toRepo(it).random()
                emit(item)
            } ?: throw Exception()
        } else {
            throw Exception(response.errorBody()?.toString())
        }
    }

    companion object {
        const val DIVIDER_OLD = " "
        const val DIVIDER = "+"
        const val EBOOK_QUERY = "ebooks"
    }
}