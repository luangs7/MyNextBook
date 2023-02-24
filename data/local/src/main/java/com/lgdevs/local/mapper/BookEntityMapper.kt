package com.lgdevs.local.mapper

import com.lgdevs.local.model.BookEntity
import com.lgdevs.local.model.BookImageEntity
import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.BookImageData

internal class BookEntityMapper {
    fun toEntity(model: BookData,
                 userId: String? = null) = BookEntity(
        model.id,
        model.title.orEmpty(),
        model.subtitle.orEmpty(),
        model.authors.orEmpty(),
        model.publisher.orEmpty(),
        model.description.orEmpty(),
        model.categories.orEmpty(),
        toEntity(model.imageLinks),
        model.language.orEmpty(),
        model.previewLink.orEmpty(),
        userId.orEmpty()
    )

    private fun toEntity(model: BookImageData?): BookImageEntity {
        return model?.let {
            BookImageEntity(
                it.smallThumbnail, it.thumbnail
            )
        } ?: BookImageEntity(String(), String())
    }

    fun toRepo(model: BookEntity) = BookData(
        model.id,
        model.title,
        model.subtitle,
        model.authors,
        model.publisher,
        model.description,
        null,
        model.categories,
        null,
        toRepo(model.imageLinks),
        model.language,
        model.previewLink.orEmpty(),
    )

    private fun toRepo(model: BookImageEntity?): BookImageData {
        return model?.let {
            BookImageData(
                it.smallThumbnail, it.thumbnail
            )
        } ?: BookImageData(String(), String())
    }
}