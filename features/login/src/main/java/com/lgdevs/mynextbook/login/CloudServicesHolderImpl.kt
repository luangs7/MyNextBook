package com.lgdevs.mynextbook.login

import com.lgdevs.mynextbook.cloudservices.analytics.CloudServicesAnalytics
import com.lgdevs.mynextbook.cloudservices.remoteconfig.CloudServicesRemoteConfig

class CloudServicesHolderImpl(
    private val remoteConfig: CloudServicesRemoteConfig,
    private val analytics: CloudServicesAnalytics,
) : CloudServicesHolder {
    override fun getRemoteConfig(): CloudServicesRemoteConfig = this.remoteConfig

    override fun getAnalytics(): CloudServicesAnalytics = this.analytics
}
