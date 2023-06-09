package com.lgdevs.mynextbook.firebase.application

import android.content.Context
import com.google.firebase.FirebaseApp
import com.lgdevs.mynextbook.cloudservices.application.CloudServicesApplication

class FirebaseApplicationImpl:
    CloudServicesApplication {
    override fun initialize(context: Context) {
        FirebaseApp.initializeApp(context)
    }
}