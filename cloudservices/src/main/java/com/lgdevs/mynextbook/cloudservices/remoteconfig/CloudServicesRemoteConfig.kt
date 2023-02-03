package com.lgdevs.mynextbook.cloudservices.remoteconfig

import androidx.lifecycle.MutableLiveData


interface CloudServicesRemoteConfig {
    var configs : MutableLiveData<HashMap<String, String>>

    suspend fun setDefaults(resourceId: Int) : Boolean
    suspend fun fetch(cacheExpiration: Long? = null) : Boolean
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
}