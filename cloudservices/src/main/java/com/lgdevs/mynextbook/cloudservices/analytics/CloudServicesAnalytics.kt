package com.lgdevs.mynextbook.cloudservices.analytics

import android.os.Bundle

interface CloudServicesAnalytics {
    suspend fun onEvent(event: String, params: Bundle)
    suspend fun setUserProperty(name: String, value: String)
    suspend fun setUserId(id: String)
}