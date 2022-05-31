package com.lgdevs.mynextbook.datastore.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.lgdevs.mynextbook.datastore.model.AppPreferenceDatastore
import com.lgdevs.mynextbook.datastore.preferences
import com.lgdevs.mynextbook.mapper.AppPreferencesMapper
import com.lgdevs.mynextbook.repository.datasource.PreferencesDataSourceDatastore
import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PreferencesDataSourceDatastoreImpl(
    private val context: Context,
    private val mapper: AppPreferencesMapper
): PreferencesDataSourceDatastore {
    override suspend fun updatePreferences(preferences: AppPreferencesRepo) {
        context.preferences.edit {
            it[PREFERENCES_KEY] = Gson().toJson(mapper.toDatastore(preferences))
        }
    }

    override suspend fun loadPreferences(): Flow<AppPreferencesRepo> =
        context.preferences.data.map {
            val result = it[PREFERENCES_KEY]
            mapper.toRepo(Gson().fromJson(result, AppPreferenceDatastore::class.java))
        }

    companion object {
        val PREFERENCES_KEY = stringPreferencesKey("prefkey")
    }
}