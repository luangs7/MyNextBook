package com.lgdevs.mynextbook.repository.model

data class AppPreferencesRepo(
    val isEbook: Boolean,
    val keyword: String?,
    val isPortuguese: Boolean,
    val subject: String?
)