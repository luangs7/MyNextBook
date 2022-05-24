package com.lgdevs.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lgdevs.local.model.BookImageEntity

internal class BookImageConverter {

    @TypeConverter
    fun fromModel(list: BookImageEntity?): String? {
        return list?.let {  Gson().toJson(it) }
    }

    @TypeConverter
    fun toModel(json: String?): BookImageEntity? {
        return json?.let {  Gson().fromJson(json, BookImageEntity::class.java)}
    }
}