package com.lgdevs.mynextbook.repository.datasource

import kotlinx.coroutines.flow.Flow

interface UserDataSourceDatastore {
    suspend fun updateEmail(email: String): Unit
    fun loadPreferences(): Flow<String>
}
