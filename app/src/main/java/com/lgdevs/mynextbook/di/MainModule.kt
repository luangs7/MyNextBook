package com.lgdevs.mynextbook.di

import com.lgdevs.mynextbook.cloudservices.messaging.CloudServicesMessageReceivedListener
import com.lgdevs.mynextbook.common.dispatcher.CoroutineDispatcherProvider
import com.lgdevs.mynextbook.common.dispatcher.CoroutineDispatcherProviderImpl
import com.lgdevs.mynextbook.main.MainViewModel
import com.lgdevs.mynextbook.notification.NotificationBuilder
import com.lgdevs.mynextbook.observer.KObserver
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.get
import org.koin.dsl.module

val LOGOUT_OBSERVER_QUALIFIER = named("logoutObserver")

val mainModule = module {
    single(LOGOUT_OBSERVER_QUALIFIER) { KObserver<Unit>() }
    viewModel { MainViewModel(get(), get(LOGOUT_OBSERVER_QUALIFIER), get()) }
    factory { NotificationBuilder(androidContext()) }

    factory {
        CloudServicesMessageReceivedListener(get<NotificationBuilder>()::onMessageReceive)
    }

    factory {
        CoroutineDispatcherProvider(CoroutineDispatcherProviderImpl::io)
    }
}
