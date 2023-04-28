package com.lgdevs.mynextbook.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lgdevs.mynextbook.repository.datasource.UserDataSourceDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class UserDataSourceDatastoreImpl(
    private val datastore: DataStore<Preferences>,
) : UserDataSourceDatastore {

    override suspend fun loadPreferences(): Flow<String> = flow {
        datastore.data.collect {
            emit(it[EMAIL_KEY].orEmpty())
        }
    }

    override suspend fun updateEmail(email: String) {
        datastore.edit {
            it[EMAIL_KEY] = email
        }
    }

    companion object {
        val EMAIL_KEY = stringPreferencesKey("emailkey")
    }
}
