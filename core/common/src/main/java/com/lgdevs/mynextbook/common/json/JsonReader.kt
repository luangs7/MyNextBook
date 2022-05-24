package com.lgdevs.mynextbook.common.json

import java.io.InputStreamReader

object JsonReader {
    fun readMockedJson(path:String):String{
        this.javaClass.classLoader?.let {
            val reader = InputStreamReader(it.getResourceAsStream(path))
            val content = reader.readText()
            reader.close()
            return content
        } ?: kotlin.run {
            return String()
        }
    }
}