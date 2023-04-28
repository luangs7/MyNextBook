package com.lgdevs.mynextbook.application

import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.lgdevs.local.di.localModule
import com.lgdevs.mynextbook.di.datastoreModule
import com.lgdevs.mynextbook.di.mainModule
import com.lgdevs.mynextbook.domain.di.domainModule
import com.lgdevs.mynextbook.filter.di.preferencesModule
import com.lgdevs.mynextbook.finder.di.finderModule
import com.lgdevs.mynextbook.firebase.di.firebaseModule
import com.lgdevs.mynextbook.di.loginModule
import com.lgdevs.mynextbook.remote.di.networkModule
import com.lgdevs.mynextbook.remote.di.remoteModule
import com.lgdevs.mynextbook.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CoreApplication : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CoreApplication)
            androidLogger(Level.DEBUG)
            modules(
                mainModule +
                        loginModule +
                datastoreModule +
                        remoteModule +
                        networkModule +
                        localModule +
                        repositoryModule +
                        domainModule +
                        preferencesModule +
                        finderModule +
                        firebaseModule
            )
        }

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}