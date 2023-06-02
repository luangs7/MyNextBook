package com.lgdevs.mynextbook.chatia.data.local.mapper

import com.lgdevs.mynextbook.chatia.data.local.model.MessageEntity
import com.lgdevs.mynextbook.chatia.data.repository.model.MessageRepo

internal class MessageEntityMapper {
    fun toRepo(model: MessageEntity): MessageRepo {
        return MessageRepo(
            model.id,
            model.userId,
            model.message,
            model.datetime,
            model.sender,
        )
    }

    fun toEntity(model: MessageRepo): MessageEntity {
        return MessageEntity(
            model.id,
            model.userId,
            model.message,
            model.datetime,
            model.sender,
        )
    }
}
