package com.lgdevs.mynextbook.common.analytics

import kotlinx.coroutines.delay

object SingleAnalyticsEvent {
    private var hasBeenHandled = false
    private var lastEvent: String? = null
    private var cacheExpiration: Long = 0

    private fun setEvent(event: String?) {
        this.lastEvent = event
    }

    fun setCacheExpiration(cacheExpiration: Long) {
        this.cacheExpiration = cacheExpiration
    }

    suspend fun getContentIfNotHandled(event: String, action: suspend () -> Unit) {
        hasBeenHandled = event == lastEvent
        if (!hasBeenHandled) {
            setEvent(event)
            action()
        }
        delay(cacheExpiration)
        setEvent(null)
    }
}
