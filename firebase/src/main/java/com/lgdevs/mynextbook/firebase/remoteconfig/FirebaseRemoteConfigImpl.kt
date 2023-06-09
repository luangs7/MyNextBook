package com.lgdevs.mynextbook.firebase.remoteconfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.lgdevs.mynextbook.cloudservices.remoteconfig.CloudServicesRemoteConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseRemoteConfigImpl :
    CloudServicesRemoteConfig {

    override var configs = MutableStateFlow(hashMapOf<String, String>())

    private val remoteConfig by lazy {
        val config = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
        configSettings.minimumFetchIntervalInSeconds = 0
        configSettings.build().let {
            config.setConfigSettingsAsync(it)
        }
        config
    }

    override suspend fun setDefaults(resourceId: Int): Boolean = suspendCoroutine {
        remoteConfig.setDefaultsAsync(resourceId).addOnCompleteListener { task ->
            it.resume(task.isSuccessful)
        }
    }

    override suspend fun fetch(cacheExpiration: Long?): Boolean = suspendCoroutine {
        if (cacheExpiration != null) {
            remoteConfig.fetch(cacheExpiration).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    remoteConfig.activate()
                    configs.tryEmit(buildConfigs())
                }

                it.resume(task.isSuccessful)
            }
        } else {
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                configs.tryEmit(buildConfigs())
                it.resume(task.isSuccessful)
            }
        }
    }

    private fun buildConfigs(): HashMap<String, String> {
        val result = HashMap<String, String>()
        remoteConfig.all.forEach {
            result[it.key] = it.value.asString()
        }
        return result
    }

    override fun getString(key: String): String {
        return remoteConfig.getString(key)
    }

    override fun getBoolean(key: String): Boolean {
        return remoteConfig.getBoolean(key)
    }
}
