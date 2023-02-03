package com.lgdevs.mynextbook.cloudservices.auth

import android.net.Uri

data class CurrentUser(
    val uuid:String,
    val name: String,
    val email: String,
    val avatar: Uri?
)
