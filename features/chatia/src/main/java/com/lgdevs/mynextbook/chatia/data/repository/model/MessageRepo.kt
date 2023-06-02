package com.lgdevs.mynextbook.chatia.data.repository.model

data class MessageRepo(
    val id: String,
    val userId: String,
    val message: String,
    val datetime: String,
    val sender: Boolean,
)
