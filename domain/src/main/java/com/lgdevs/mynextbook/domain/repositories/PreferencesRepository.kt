package com.lgdevs.mynextbook.domain.repositories

import com.lgdevs.mynextbook.domain.model.AppPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun updatePreferences(preferences: AppPreferences)
    suspend fun loadPreferences(): Flow<AppPreferences>
}