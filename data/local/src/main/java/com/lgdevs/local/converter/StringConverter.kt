package com.lgdevs.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class StringConverter {

    @TypeConverter
    fun fromList(list: List<String>?): String? {
        return list?.let {  Gson().toJson(it) }
    }

    @TypeConverter
    fun toList(json: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return json?.let {  Gson().fromJson(json, type)}

    }
}