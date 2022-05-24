package com.lgdevs.local.model

import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.BookImageData

fun BookData.toEntity() = BookEntity(
    this.id,
    this.title.orEmpty(),
    this.subtitle.orEmpty(),
    this.authors.orEmpty(),
    this.publisher.orEmpty(),
    this.description.orEmpty(),
    this.categories.orEmpty(),
    this.imageLinks?.toEntity() ?: BookImageEntity(String(), String()),
    this.language.orEmpty(),
    this.previewLink.orEmpty(),
)

fun BookImageData.toEntity() = BookImageEntity(
    this.smallThumbnail, this.thumbnail
)

fun BookEntity.toRepo() = BookData(
    this.id,
    this.title,
    this.subtitle,
    this.authors,
    this.publisher,
    this.description,
    null,
    this.categories,
    null,
    this.imageLinks?.toRepo(),
    this.language,
    this.previewLink.orEmpty(),
)


fun BookImageEntity.toRepo() = BookImageData(
    this.smallThumbnail, this.thumbnail
)