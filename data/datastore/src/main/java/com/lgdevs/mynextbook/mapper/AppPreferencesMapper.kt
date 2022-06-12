package com.lgdevs.mynextbook.mapper

import com.lgdevs.mynextbook.datastore.model.AppPreferenceDatastore
import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo

internal class AppPreferencesMapper {
    fun toDatastore(model: AppPreferencesRepo) = AppPreferenceDatastore(
        model.isEbook,
        model.keyword,
        model.isPortuguese,
        model.subject
    )

    fun toRepo(model: AppPreferenceDatastore) = AppPreferencesRepo(
        model.isEbook,
        model.keyword,
        model.isPortuguese,
        model.subject
    )
}