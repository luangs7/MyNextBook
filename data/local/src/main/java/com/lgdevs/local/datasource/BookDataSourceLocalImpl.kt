package com.lgdevs.local.datasource

import com.lgdevs.local.dao.BookDao
import com.lgdevs.local.mapper.BookEntityMapper
import com.lgdevs.mynextbook.repository.datasource.BookDataSourceLocal
import com.lgdevs.mynextbook.repository.model.BookData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class BookDataSourceLocalImpl(
    private val dao: BookDao,
    private val mapper: BookEntityMapper
) : BookDataSourceLocal {

    override suspend fun getFavoriteBook(id: String): Flow<BookData?> = flow {
        val item = dao.getFavoritesById(id)
        item?.let { emit(mapper.toRepo(it)) } ?: emit(null)
    }.flowOn(Dispatchers.IO)

    override suspend fun getFavoritesBooks(userId: String): Flow<List<BookData>> = flow {
        val list = dao.getFavorites(userId)
        emit(list.map { mapper.toRepo(it) })
    }.flowOn(Dispatchers.IO)

    override suspend fun removeFavoriteBook(book: BookData): Flow<Unit> = flow {
        emit(dao.delete(mapper.toEntity(book)))
    }.flowOn(Dispatchers.IO)

    override suspend fun setFavoriteBook(book: BookData, userId: String): Flow<Unit> = flow {
        emit(dao.insertBook(mapper.toEntity(book, userId)))
    }.flowOn(Dispatchers.IO)
}