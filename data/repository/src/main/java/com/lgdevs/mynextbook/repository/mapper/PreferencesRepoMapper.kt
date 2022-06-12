package com.lgdevs.mynextbook.repository.mapper

import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo

internal class PreferencesRepoMapper {
    fun toRepo(model: AppPreferences): AppPreferencesRepo {
        return AppPreferencesRepo(
            model.isEbook,
            model.keyword,
            model.isPortuguese,
            model.subject
        )
    }

    fun toDomain(model: AppPreferencesRepo) =
        AppPreferences(model.isEbook, model.keyword, model.isPortuguese, model.subject)

}