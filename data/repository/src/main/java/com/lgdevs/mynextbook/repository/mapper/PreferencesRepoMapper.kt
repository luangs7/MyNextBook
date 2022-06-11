package com.lgdevs.mynextbook.repository.mapper

import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.model.BookImage
import com.lgdevs.mynextbook.repository.model.AppPreferencesRepo
import com.lgdevs.mynextbook.repository.model.BookData
import com.lgdevs.mynextbook.repository.model.BookImageData

internal class PreferencesRepoMapper {
    fun toRepo(model: AppPreferences): AppPreferencesRepo {
        return AppPreferencesRepo(
            model.isEbook,
            model.keyword,
            model.language,
            model.subject
        )
    }

    fun toDomain(model: AppPreferencesRepo) =
        AppPreferences(model.isEbook, model.keyword, model.language, model.subject)

}