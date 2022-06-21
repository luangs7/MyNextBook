package com.lgdevs.mynextbook.di

import com.lgdevs.mynextbook.datastore.datasource.PreferencesDataSourceDatastoreImpl
import com.lgdevs.mynextbook.mapper.AppPreferencesMapper
import com.lgdevs.mynextbook.repository.datasource.PreferencesDataSourceDatastore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val datastoreModule = module {

    factory<PreferencesDataSourceDatastore> { PreferencesDataSourceDatastoreImpl(androidContext(), get()) }
    single { AppPreferencesMapper() }

}