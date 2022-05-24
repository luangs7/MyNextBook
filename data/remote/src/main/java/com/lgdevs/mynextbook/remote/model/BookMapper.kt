package com.lgdevs.mynextbook.remote.model

import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.BookImageData

fun BookResponse.toRepo():List<BookData>{
    return items.map {
        BookData(
            it.id,
            it.volumeInfo.title,
            it.volumeInfo.subtitle,
            it.volumeInfo.authors,
            it.volumeInfo.publisher,
            it.volumeInfo.description,
            it.volumeInfo.pageCount,
            it.volumeInfo.categories,
            it.volumeInfo.contentVersion,
            it.volumeInfo.imageLinks?.toRepo(),
            it.volumeInfo.language,
            it.volumeInfo.previewLink,
            it.volumeInfo.infoLink,
            it.volumeInfo.averageRating,
            it.volumeInfo.ratingsCount,
            it.volumeInfo.publishedDate
        )
    }
}

fun ImageLinks.toRepo(): BookImageData = BookImageData(
    this.thumbnail, this.smallThumbnail
)