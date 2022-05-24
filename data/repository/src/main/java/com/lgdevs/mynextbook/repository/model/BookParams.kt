package com.lgdevs.mynextbook.repository.model

data class BookParams(
    val isEbook: Boolean,
    val keyword: String?,
    val language: String?,
    val subject: String?
)