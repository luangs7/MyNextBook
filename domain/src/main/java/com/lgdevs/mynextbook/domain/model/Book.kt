package com.lgdevs.mynextbook.domain.model

data class Book(
    val id: String,
    val title: String? = null,
    val subtitle: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val description: String? = null,
    val pageCount: Long? = null,
    val categories: List<String>? = null,
    val contentVersion: String? = null,
    val imageLinks: BookImage? = null,
    val language: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val averageRating: String? = null,
    val ratingsCount: Long? = null,
    val publishedDate: String? = null
)