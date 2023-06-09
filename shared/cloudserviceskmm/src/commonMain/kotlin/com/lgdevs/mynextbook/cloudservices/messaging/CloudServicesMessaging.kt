package com.lgdevs.mynextbook.cloudservices.messaging

interface CloudServicesMessaging {
    suspend fun getToken(): String
    suspend fun onSubscribe(topic: String)
    suspend fun onUnsubscribe(topic: String)
}
