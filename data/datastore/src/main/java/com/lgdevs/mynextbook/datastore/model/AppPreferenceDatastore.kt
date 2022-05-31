package com.lgdevs.mynextbook.datastore.model

data class AppPreferenceDatastore(
    val isEbook: Boolean = false,
    val keyword: String? = null,
    val language: String? = null,
    val subject: String? = null
)