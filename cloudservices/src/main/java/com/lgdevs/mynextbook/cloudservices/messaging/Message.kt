package com.lgdevs.mynextbook.cloudservices.messaging

data class Message(
    val title: String?,
    val data: Map<String, String>?,
    val notification: String?,
)
