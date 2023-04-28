package com.lgdevs.mynextbook.domain.model

import android.net.Uri

data class User(
    val uuid:String,
    val name: String,
    val email: String,
    val avatar: Uri?
)