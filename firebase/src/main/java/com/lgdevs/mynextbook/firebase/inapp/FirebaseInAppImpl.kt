package com.lgdevs.mynextbook.firebase.inapp

import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.lgdevs.mynextbook.cloudservices.inapp.CloudServicesInApp
import org.koin.core.component.KoinComponent

class FirebaseInAppImpl: CloudServicesInApp, KoinComponent {
    private val inAppMessaging: FirebaseInAppMessaging by lazy { FirebaseInAppMessaging.getInstance() }

    override fun setMessagesSuppressed(isSuppressed: Boolean) {
        inAppMessaging.setMessagesSuppressed(isSuppressed)
    }

    override fun setAutomaticDataCollectionEnabled(enabled: Boolean) {
        inAppMessaging.isAutomaticDataCollectionEnabled = enabled
    }
}