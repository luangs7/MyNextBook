package com.lgdevs.mynextbook.cloudservices.messaging

interface CloudServicesMessageListener {
    fun onMessageReceived(message: Message)
    fun onNewToken(token: String)
}