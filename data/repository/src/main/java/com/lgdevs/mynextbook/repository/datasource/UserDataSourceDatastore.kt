package com.lgdevs.mynextbook.repository.datasource

import kotlinx.coroutines.flow.Flow

interface UserDataSourceDatastore {
    suspend fun updateEmail(email: String): Unit
    suspend fun loadPreferences(): Flow<String>
}
