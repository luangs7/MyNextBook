package com.lgdevs.mynextbook.remote.datasource

import com.lgdevs.mynextbook.remote.mapper.BookRemoteMapper
import com.lgdevs.mynextbook.remote.service.BookService
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceRemote
import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class BookDataSourceRemoteImpl(
    private val service: BookService,
    private val mapper: BookRemoteMapper
) : BookDataSourceRemote {
    override suspend fun getBooksFromQuery(appPreferencesRepo: AppPreferencesRepo): Flow<BookData> =
        flow {
            val filter = if (appPreferencesRepo.isEbook) EBOOK_QUERY else null
            val query = createQueryParams(appPreferencesRepo)
            val response = service.getBooks(
                query,
                if (appPreferencesRepo.isPortuguese) LANGUAGE_PT else null,
                filter
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    val item = mapper.toRepo(it).random()
                    emit(item)
                } ?: throw Exception()
            } else {
                throw Exception(response.errorBody()?.toString())
            }
        }


    private fun createQueryParams(appPreferencesRepo: AppPreferencesRepo): String {
        val query = StringBuilder().also {
            val keyword = appPreferencesRepo.keyword?.replace(DIVIDER_OLD, DIVIDER)
            if (keyword.isNullOrEmpty().not()) {
                it.append(keyword)
            }
        }
        return query.toString()
    }

    companion object {
        const val DIVIDER_OLD = " "
        const val DIVIDER = "+"
        const val EBOOK_QUERY = "ebooks"
        const val LANGUAGE_PT = "pt"
        const val LANGUAGE_RESTRICT_QUERY = "langRestrict"
        const val QUERY = "q"
        const val FILTER_QUERY = "filter"
        const val ORDERBY_QUERY = "orderBy"
        const val ORDERBY_QUERY_VALUE = "relevance"
        const val MAXRESULTS_QUERY = "maxResults"
        const val MAXRESULTS_VALUE = 40
        const val QUERY_DIVIDER = "&"
    }
}