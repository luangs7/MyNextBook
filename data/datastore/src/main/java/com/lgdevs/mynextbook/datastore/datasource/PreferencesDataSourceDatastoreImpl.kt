package com.lgdevs.mynextbook.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.lgdevs.mynextbook.datastore.model.AppPreferenceDatastore
import com.lgdevs.mynextbook.mapper.AppPreferencesMapper
import com.lgdevs.mynextbook.repository.datasource.PreferencesDataSourceDatastore
import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PreferencesDataSourceDatastoreImpl(
    private val datastore: DataStore<Preferences>,
    private val mapper: AppPreferencesMapper,
) : PreferencesDataSourceDatastore {
    override suspend fun updatePreferences(preferences: AppPreferencesRepo, userId: String) {
        datastore.edit {
            it[getPreferenceKey(userId)] = Gson().toJson(mapper.toDatastore(preferences))
        }
    }

    override fun loadPreferences(userId: String): Flow<AppPreferencesRepo> =
        datastore.data.map {
            val result = it[getPreferenceKey(userId)] ?: Gson().toJson(createDefaultPreferences())
            mapper.toRepo(Gson().fromJson(result, AppPreferenceDatastore::class.java))
        }

    private fun createDefaultPreferences(): AppPreferenceDatastore =
        AppPreferenceDatastore(false, null, false, null)

    private fun getPreferenceKey(param: String) = stringPreferencesKey("prefkey_$param")
}
