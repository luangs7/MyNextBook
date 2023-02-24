package com.lgdevs.mynextbook.firebase.messaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lgdevs.mynextbook.cloudservices.messaging.CloudServicesMessageReceivedListener
import com.lgdevs.mynextbook.cloudservices.messaging.CloudServicesMessageTokenListener
import com.lgdevs.mynextbook.cloudservices.messaging.Message
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent
import com.lgdevs.mynextbook.common.koin.injectOrNullable

class FirebaseMessagingServiceImpl : FirebaseMessagingService() {

    private val cloudServicesMessageTokenListener: CloudServicesMessageTokenListener? = injectOrNullable()
    private val cloudServicesMessageReceivedListener: CloudServicesMessageReceivedListener? = injectOrNullable()

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        cloudServicesMessageTokenListener?.onNewToken(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        cloudServicesMessageReceivedListener?.onMessageReceived(p0.toMessage())
    }
}

fun RemoteMessage.toMessage(): Message {
    return Message(
        title = notification?.title,
        notification = notification?.body,
        data = data
    )
}