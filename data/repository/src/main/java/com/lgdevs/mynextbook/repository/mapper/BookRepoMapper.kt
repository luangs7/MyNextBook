package com.lgdevs.mynextbook.repository.mapper

import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.BookImage
import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo
import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.BookImageData

internal class BookRepoMapper {
    fun toRepo(model: Book): BookData {
        return BookData(
            model.id,
            model.title,
            model.subtitle,
            model.authors,
            model.publisher,
            model.description,
            model.pageCount,
            model.categories,
            model.contentVersion,
            toRepo(model.imageLinks),
            model.language,
            model.previewLink,
            model.infoLink,
            model.averageRating,
            model.ratingsCount,
            model.publishedDate
        )
    }

    fun toRepo(model: BookImage?) =
        model?.let { BookImageData(model.smallThumbnail, model.thumbnail) }

    fun toDomain(model: BookData) = Book(
        model.id,
        model.title,
        model.subtitle,
        model.authors,
        model.publisher,
        model.description,
        model.pageCount,
        model.categories,
        model.contentVersion,
        toDomain(model.imageLinks),
        model.language,
        model.previewLink,
        model.infoLink,
        model.averageRating,
        model.ratingsCount,
        model.publishedDate
    )

    private fun toDomain(model: BookImageData?) =
        model?.let { BookImage(it.smallThumbnail, it.thumbnail) }
}