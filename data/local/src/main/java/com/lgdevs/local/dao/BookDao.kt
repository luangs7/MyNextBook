package com.lgdevs.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lgdevs.local.model.BookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: BookEntity)

    @Query("SELECT * FROM bookentity WHERE userId == :userId")
    fun getFavorites(userId: String): List<BookEntity>

    @Query("SELECT * FROM bookentity WHERE id == :bookId")
    fun getFavoritesById(bookId: String): BookEntity?

    @Delete
    fun delete(book: BookEntity)
}
