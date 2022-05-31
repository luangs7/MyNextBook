package com.lgdevs.mynextbook.domain.model

data class AppPreferences(
    val isEbook: Boolean,
    val keyword: String?,
    val language: String?,
    val subject: String?
)