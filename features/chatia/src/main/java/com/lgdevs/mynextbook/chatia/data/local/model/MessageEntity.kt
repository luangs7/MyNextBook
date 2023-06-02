package com.lgdevs.mynextbook.chatia.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MessageEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val message: String,
    val datetime: String,
    val sender: Boolean
)
