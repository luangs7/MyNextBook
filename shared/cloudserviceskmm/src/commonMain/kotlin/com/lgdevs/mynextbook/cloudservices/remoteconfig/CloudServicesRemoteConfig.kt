package com.lgdevs.mynextbook.cloudservices.remoteconfig

import kotlinx.coroutines.flow.MutableStateFlow

interface CloudServicesRemoteConfig {
    var configs: MutableStateFlow<HashMap<String, String>>

    suspend fun setDefaults(resourceId: Int): Boolean
    suspend fun fetch(cacheExpiration: Long? = null): Boolean
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
}
