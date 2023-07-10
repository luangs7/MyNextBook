package com.lgdevs.mynextbook.remote.mapper

import com.lgdevs.mynextbook.remote.model.BookResponse
import com.lgdevs.mynextbook.remote.model.ImageLinks
import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.BookImageData

internal class BookRemoteMapper {
    fun toRepo(model: BookResponse): List<BookData> {
        return model.items?.map {
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
                toRepo(it.volumeInfo.imageLinks),
                it.volumeInfo.language,
                it.volumeInfo.previewLink,
                it.volumeInfo.infoLink,
                it.volumeInfo.averageRating,
                it.volumeInfo.ratingsCount,
                it.volumeInfo.publishedDate,
            )
        } ?: emptyList()
    }

    private fun toRepo(model: ImageLinks?): BookImageData? {
        return model?.let {
            BookImageData(
                it.smallThumbnail,
                it.thumbnail.replace("zoom=", "zoom=5"),
            )
        }
    }
}
