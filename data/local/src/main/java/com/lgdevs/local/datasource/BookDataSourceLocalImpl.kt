package com.lgdevs.local.datasource

import com.lgdevs.local.dao.BookDao
import com.lgdevs.local.mapper.BookEntityMapper
import com.lgdevs.local.model.toEntity
import com.lgdevs.local.model.toRepo
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceLocal
import com.lgdevs.mynextbook.repository.model.BookData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

internal class BookDataSourceLocalImpl(
    private val dao: BookDao,
    private val mapper: BookEntityMapper
) : BookDataSourceLocal {

    override suspend fun getFavoriteBook(id: String): Flow<BookData?> = flow {
        val item = dao.getFavoritesById(id)
        item?.let { emit(mapper.toRepo(it)) } ?: emit(null)
    }.flowOn(Dispatchers.IO)

    override suspend fun getFavoritesBooks(): Flow<List<BookData>> = flow {
        val list = dao.getFavorites()
        emit(list.map { mapper.toRepo(it) })
    }.flowOn(Dispatchers.IO)

    override suspend fun removeFavoriteBook(book: BookData): Flow<Unit> = flow {
        emit(dao.delete(mapper.toEntity(book)))
    }.flowOn(Dispatchers.IO)

    override suspend fun setFavoriteBook(book: BookData): Flow<Unit> = flow {
        emit(dao.insertBook(mapper.toEntity(book)))
    }.flowOn(Dispatchers.IO)
}