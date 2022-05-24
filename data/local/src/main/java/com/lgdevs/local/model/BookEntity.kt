package com.lgdevs.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val authors: List<String>,
    val publisher: String,
    val description: String,
    val categories: List<String>,
    val imageLinks: BookImageEntity,
    val language: String,
    val previewLink: String?
)