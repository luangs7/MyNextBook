package com.lgdevs.mynextbook.repository.model

import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.BookImage
import com.lgdevs.mynextbook.domain.model.GetBookParams

fun Book.toRepo() = BookData(
    this.id,
    this.title,
    this.subtitle,
    this.authors,
    this.publisher,
    this.description,
    this.pageCount,
    this.categories,
    this.contentVersion,
    this.imageLinks?.toRepo(),
    this.language,
    this.previewLink,
    this.infoLink,
    this.averageRating,
    this.ratingsCount,
    this.publishedDate
)

fun BookImage.toRepo() = BookImageData(this.smallThumbnail, this.thumbnail)

fun BookData.toDomain() = Book(
    this.id,
    this.title,
    this.subtitle,
    this.authors,
    this.publisher,
    this.description,
    this.pageCount,
    this.categories,
    this.contentVersion,
    this.imageLinks?.toDomain(),
    this.language,
    this.previewLink,
    this.infoLink,
    this.averageRating,
    this.ratingsCount,
    this.publishedDate
)

fun BookImageData.toDomain() = BookImage(this.smallThumbnail, this.thumbnail)

fun BookParams.toDomain() = GetBookParams(isEbook, keyword, language, subject)
fun GetBookParams.toRepo() = BookParams(isEbook, keyword, language, subject)