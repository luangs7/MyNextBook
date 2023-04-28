package com.lgdevs.mynextbook.firebase.messaging

import com.google.firebase.messaging.FirebaseMessaging
import com.lgdevs.mynextbook.cloudservices.messaging.CloudServicesMessaging
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent

class FirebaseMessagingImpl: CloudServicesMessaging, KoinComponent {
    private val messaging: FirebaseMessaging by lazy { FirebaseMessaging.getInstance() }

    override suspend fun getToken(): String {
        return messaging.token.await()
    }

    override suspend fun onSubscribe(topic: String) {
        messaging.subscribeToTopic(topic)
    }

    override suspend fun onUnsubscribe(topic: String) {
        messaging.unsubscribeFromTopic(topic)
    }
}