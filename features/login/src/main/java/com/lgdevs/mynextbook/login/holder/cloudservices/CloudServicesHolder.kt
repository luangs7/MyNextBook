package com.lgdevs.mynextbook.login.holder.cloudservices

import com.lgdevs.mynextbook.cloudservices.analytics.CloudServicesAnalytics
import com.lgdevs.mynextbook.cloudservices.remoteconfig.CloudServicesRemoteConfig

interface CloudServicesHolder {
    fun getRemoteConfig(): CloudServicesRemoteConfig
    fun getAnalytics(): CloudServicesAnalytics
}
