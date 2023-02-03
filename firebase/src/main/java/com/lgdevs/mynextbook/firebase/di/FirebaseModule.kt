package com.lgdevs.mynextbook.firebase.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.lgdevs.mynextbook.cloudservices.analytics.CloudServicesAnalytics
import com.lgdevs.mynextbook.cloudservices.application.CloudServicesApplication
import com.lgdevs.mynextbook.cloudservices.auth.CloudServicesAuth
import com.lgdevs.mynextbook.cloudservices.firestore.CloudServicesRealTimeDatabase
import com.lgdevs.mynextbook.cloudservices.inapp.CloudServicesInApp
import com.lgdevs.mynextbook.cloudservices.messaging.CloudServicesMessaging
import com.lgdevs.mynextbook.cloudservices.remoteconfig.CloudServicesRemoteConfig
import com.lgdevs.mynextbook.firebase.analytics.FirebaseAnalyticsImpl
import com.lgdevs.mynextbook.firebase.application.FirebaseApplicationImpl
import com.lgdevs.mynextbook.firebase.auth.FirebaseAuthImpl
import com.lgdevs.mynextbook.firebase.firestore.FirebaseFirestoreQueryImpl
import com.lgdevs.mynextbook.firebase.inapp.FirebaseInAppImpl
import com.lgdevs.mynextbook.firebase.messaging.FirebaseMessagingImpl
import com.lgdevs.mynextbook.firebase.remoteconfig.FirebaseRemoteConfigImpl
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseAnalyticsImpl() as CloudServicesAnalytics }
    single { FirebaseMessagingImpl() as CloudServicesMessaging }
    single { FirebaseApplicationImpl() as CloudServicesApplication }
    single { FirebaseRemoteConfigImpl() as CloudServicesRemoteConfig }
    single { FirebaseFirestoreQueryImpl() as CloudServicesRealTimeDatabase }
    single { FirebaseInAppImpl() as CloudServicesInApp }
    single { FirebaseAuthImpl() as CloudServicesAuth }


    single { FirebaseAnalytics.getInstance(get()) }
}