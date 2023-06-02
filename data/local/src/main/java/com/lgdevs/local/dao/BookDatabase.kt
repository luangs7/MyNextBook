package com.lgdevs.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lgdevs.local.converter.BookImageConverter
import com.lgdevs.local.converter.StringConverter
import com.lgdevs.local.model.BookEntity
import com.lgdevs.local.model.BookImageEntity

@Database(entities = [BookEntity::class, BookImageEntity::class], version = 3, exportSchema = false)
@TypeConverters(StringConverter::class, BookImageConverter::class)
abstract class BookDatabase: RoomDatabase() {
    abstract val dao: BookDao
}