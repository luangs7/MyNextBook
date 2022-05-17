package com.lgdevs.mynextbook.remote.model

data class BookResponse(
    val totalItems: Long,
    val items: List<Item>
)

data class Item (
    val id: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo (
    val title: String,
    val subtitle: String? = null,
    val authors: List<String>,
    val publisher: String,
    val description: String? = null,
    val pageCount: Long,
    val categories: List<String>,
    val allowAnonLogging: Boolean,
    val contentVersion: String,
    val imageLinks: ImageLinks,
    val language: String,
    val previewLink: String,
    val infoLink: String,
    val canonicalVolumeLink: String,
    val averageRating: Long? = null,
    val ratingsCount: Long? = null,
    val publishedDate: String? = null
)

data class ImageLinks (
    val smallThumbnail: String,
    val thumbnail: String
)


