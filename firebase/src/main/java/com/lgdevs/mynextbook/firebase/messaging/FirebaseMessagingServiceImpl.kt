package com.lgdevs.mynextbook.firebase.messaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lgdevs.mynextbook.cloudservices.messaging.CloudServicesMessageListener
import com.lgdevs.mynextbook.cloudservices.messaging.Message
import org.koin.android.ext.android.inject

class FirebaseMessagingServiceImpl : FirebaseMessagingService() {

    private val cloudServicesMessageListener: CloudServicesMessageListener by inject()

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        cloudServicesMessageListener.onNewToken(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        cloudServicesMessageListener.onMessageReceived(p0.toMessage())
    }
}

fun RemoteMessage.toMessage(): Message {
    return Message(
        title = notification?.title,
        notification = notification?.body,
        data = data
    )
}