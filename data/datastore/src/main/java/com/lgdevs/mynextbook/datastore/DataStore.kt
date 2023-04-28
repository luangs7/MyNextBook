package com.lgdevs.mynextbook.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

const val DATASTORE_SETTINGS = "mynextbook_settings"
const val DATASTORE_SETTINGS_TEST = "mynextbook_settings_test"

val Context.preferences by preferencesDataStore(name = DATASTORE_SETTINGS)
val Context.testPreferences by preferencesDataStore(name = DATASTORE_SETTINGS_TEST)


