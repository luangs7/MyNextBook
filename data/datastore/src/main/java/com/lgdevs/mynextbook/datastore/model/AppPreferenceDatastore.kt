package com.lgdevs.mynextbook.datastore.model

data class AppPreferenceDatastore(
    val isEbook: Boolean,
    val keyword: String?,
    val language: String?,
    val subject: String?
)

