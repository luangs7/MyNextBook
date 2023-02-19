package com.lgdevs.mynextbook.datastore.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lgdevs.mynextbook.datastore.preferences
import com.lgdevs.mynextbook.repository.datasource.UserDataSourceDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class UserDataSourceDatastoreImpl(private val context: Context) : UserDataSourceDatastore {

    override suspend fun loadPreferences(): Flow<String> = flow {
        context.preferences.data.collect {
            emit(it[EMAIL_KEY].orEmpty())
        }
    }

    override suspend fun updateEmail(email: String) = flow{
        context.preferences.edit {
            it[EMAIL_KEY] = email
            emit(Unit)
        }
    }

    companion object {
        val EMAIL_KEY = stringPreferencesKey("emailkey")
    }
}