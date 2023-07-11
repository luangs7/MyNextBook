package com.lgdevs.mynextbook.login.analytics

import android.os.Bundle
import com.lgdevs.mynextbook.cloudservices.analytics.CloudServicesAnalytics
import com.lgdevs.mynextbook.domain.model.User

class LoginAnalyticsImpl(
    private val cloudServicesAnalytics: CloudServicesAnalytics,
) : LoginAnalytics {
    override suspend fun onEvent(event: String, params: Bundle) {
        cloudServicesAnalytics.onEvent(event, params)
    }

    override suspend fun setUserParameters(params: Bundle) {
        params.keySet().map {
            if (params.get(it) is String) {
                cloudServicesAnalytics.setUserProperty(it, params.get(it) as String)
            }
        }
    }

    override suspend fun setUserId(param: String) {
        cloudServicesAnalytics.setUserId(param)
    }

    override fun createParams(param: User): Bundle {
        return Bundle().also {
            it.putString(LoginAnalytics.LOGIN_USERNAME, param.name)
            it.putString(LoginAnalytics.LOGIN_EMAIL, param.email)
        }
    }

    override suspend fun logException(event: String, throwable: Throwable): Bundle {
        return Bundle().apply {
            putString(event, throwable::class.java.name)
        }.also {
            onEvent(event, it)
        }
    }
}
