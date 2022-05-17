package com.lgdevs.mynextbook.repository.model

data class BookData(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val authors: List<String>,
    val publisher: String,
    val description: String? = null,
    val pageCount: Long,
    val categories: List<String>,
    val contentVersion: String,
    val imageLinks: BookImageData,
    val language: String,
    val previewLink: String,
    val infoLink: String,
    val averageRating: Long? = null,
    val ratingsCount: Long? = null,
    val publishedDate: String? = null
)

data class BookImageData (
    val smallThumbnail: String,
    val thumbnail: String
)