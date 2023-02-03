package com.lgdevs.mynextbook.firebase.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.lgdevs.mynextbook.cloudservices.analytics.CloudServicesAnalytics
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FirebaseAnalyticsImpl : CloudServicesAnalytics, KoinComponent {

    private val analytics: FirebaseAnalytics by inject()

    override suspend fun onEvent(event: String, params: Bundle) {
        analytics.logEvent(event, params)
    }

    override suspend fun setUserProperty(name: String, value: String) {
        analytics.setUserProperty(name, value)
    }

    override suspend fun setUserId(id: String) {
        analytics.setUserId(id)
    }
}