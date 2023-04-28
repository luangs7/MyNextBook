package com.lgdevs.mynextbook.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.cloudservices.auth.CloudServicesAuth
import com.lgdevs.mynextbook.cloudservices.messaging.CloudServicesMessaging
import com.lgdevs.mynextbook.observer.KObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val cloudServicesAuth: CloudServicesAuth,
    private val logoutObserver: KObserver<Unit>,
    cloudServicesMessaging: CloudServicesMessaging
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            _isLoading.value = false
            cloudServicesMessaging.onSubscribe("general")
        }
    }

    fun logout() {
        viewModelScope.launch {
            cloudServicesAuth.signOut().collect {
                logoutObserver.invoke(it)
            }
        }
    }
}