package com.lgdevs.mynextbook.repository.implementation

import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.repositories.PreferencesRepository
import com.lgdevs.mynextbook.repository.datasource.PreferencesDataSourceDatastore
import com.lgdevs.mynextbook.repository.mapper.PreferencesRepoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PreferencesRepositoryImpl(
    private val dataSourceDatastore: PreferencesDataSourceDatastore,
    private val mapper: PreferencesRepoMapper
) : PreferencesRepository {
    override suspend fun loadPreferences(): Flow<AppPreferences> =
        dataSourceDatastore.loadPreferences().map { mapper.toDomain(it) }

    override suspend fun updatePreferences(preferences: AppPreferences) {
        dataSourceDatastore.updatePreferences(mapper.toRepo(preferences))
    }
}