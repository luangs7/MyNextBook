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
    val authors: List<String>? = null,
    val publisher: String? = null,
    val description: String? = null,
    val pageCount: Long? = null,
    val categories: List<String>? = null,
    val allowAnonLogging: Boolean? = null,
    val contentVersion: String? = null,
    val imageLinks: ImageLinks? = null,
    val language: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val canonicalVolumeLink: String? = null,
    val averageRating: String? = null,
    val ratingsCount: Long? = null,
    val publishedDate: String? = null
)

data class ImageLinks (
    val smallThumbnail: String,
    val thumbnail: String
)


