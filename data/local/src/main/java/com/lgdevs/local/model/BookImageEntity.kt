package com.lgdevs.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookImageEntity (
    @PrimaryKey val smallThumbnail: String,
    val thumbnail: String
)

