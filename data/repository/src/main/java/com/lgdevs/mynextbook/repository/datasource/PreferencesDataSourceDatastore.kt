package com.lgdevs.mynextbook.repository.datasource

import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo
import kotlinx.coroutines.flow.Flow

interface PreferencesDataSourceDatastore {
    suspend fun updatePreferences(preferences: AppPreferencesRepo, userId: String)
    suspend fun loadPreferences(userId: String): Flow<AppPreferencesRepo>
}