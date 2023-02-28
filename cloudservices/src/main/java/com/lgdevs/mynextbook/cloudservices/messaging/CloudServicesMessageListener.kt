package com.lgdevs.mynextbook.cloudservices.messaging

fun interface CloudServicesMessageTokenListener {
    fun onNewToken(token: String)
}

fun interface CloudServicesMessageReceivedListener {
    fun onMessageReceived(message: Message)
}