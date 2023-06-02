package com.lgdevs.mynextbook.chatia.data.repository.mapper

import com.lgdevs.mynextbook.chatia.data.repository.model.MessageRepo
import com.lgdevs.mynextbook.chatia.domain.model.Message

internal class MessageRepoMapper {
    fun toRepo(model: Message): MessageRepo {
        return MessageRepo(
            model.id,
            model.userId,
            model.message,
            model.datetime,
            model.sender,
        )
    }

    fun toDomain(model: MessageRepo): Message {
        return Message(
            model.id,
            model.userId,
            model.message,
            model.datetime,
            model.sender,
        )
    }
}
