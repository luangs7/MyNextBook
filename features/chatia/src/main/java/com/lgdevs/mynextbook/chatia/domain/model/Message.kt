package com.lgdevs.mynextbook.chatia.domain.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.UUID

data class Message(
    val id: String,
    val userId: String,
    val message: String,
    val datetime: String,
    val sender: Boolean,
)

fun createMessage(userId: String, message: String, sender: Boolean): Message {
    return Message(
        UUID.randomUUID().toString(),
        userId,
        message,
        currentTime(),
        sender,
    )
}

private fun currentTime(): String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm")
    return formatter.format(time)
}
