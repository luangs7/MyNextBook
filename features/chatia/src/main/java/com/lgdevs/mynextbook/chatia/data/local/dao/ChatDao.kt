package com.lgdevs.mynextbook.chatia.data.local.dao

import androidx.room.Insert
import androidx.room.Dao
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.lgdevs.mynextbook.chatia.data.local.model.MessageEntity

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: MessageEntity)

    @Query("SELECT * FROM messageentity WHERE userId == :userId")
    fun query(userId: String): List<MessageEntity>
}
