package com.lgdevs.mynextbook.chatia.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lgdevs.mynextbook.chatia.data.local.model.MessageEntity

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {
    abstract val dao: ChatDao
}
