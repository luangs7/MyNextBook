package com.lgdevs.mynextbook.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

const val DATASTORE_SETTINGS = "mynextbook_settings"

val Context.preferences by preferencesDataStore(name = DATASTORE_SETTINGS)


