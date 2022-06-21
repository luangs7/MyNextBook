package com.lgdevs.local.dao

import androidx.room.*
import com.lgdevs.local.model.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book:BookEntity)
    @Query("SELECT * FROM bookentity")
    fun getFavorites(): List<BookEntity>
    @Query("SELECT * FROM bookentity WHERE id == :bookId")
    fun getFavoritesById(bookId: String): BookEntity?
    @Delete
    fun delete(book: BookEntity)
}