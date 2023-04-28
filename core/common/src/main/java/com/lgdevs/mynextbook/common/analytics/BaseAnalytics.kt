package com.lgdevs.mynextbook.common.analytics

import android.os.Bundle

interface BaseAnalytics {
    suspend fun onEvent(event: String, params: Bundle)
    suspend fun setUserParameters(params: Bundle)
    suspend fun setUserId(param: String)
    suspend fun logException(event: String, throwable: Throwable): Bundle
}
