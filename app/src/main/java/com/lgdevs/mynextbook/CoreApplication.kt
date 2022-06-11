package com.lgdevs.mynextbook

import android.app.Application
import com.lgdevs.local.di.localModule
import com.lgdevs.mynextbook.di.datastoreModule
import com.lgdevs.mynextbook.domain.di.domainModule
import com.lgdevs.mynextbook.filter.di.preferencesModule
import com.lgdevs.mynextbook.remote.di.remoteModule
import com.lgdevs.mynextbook.repository.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CoreApplication)
            androidLogger(Level.DEBUG)
            modules(
                datastoreModule +
                        remoteModule +
                        localModule +
                        repositoryModule +
                        domainModule +
                        preferencesModule
            )
        }

    }
}