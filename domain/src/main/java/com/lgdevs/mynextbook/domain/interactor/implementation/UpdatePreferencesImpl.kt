package com.lgdevs.mynextbook.domain.interactor.implementation

import android.preference.Preference
import com.lgdevs.mynextbook.common.base.ApiResult
import com.lgdevs.mynextbook.domain.interactor.abstraction.AddFavoriteBook
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetPreferences
import com.lgdevs.mynextbook.domain.interactor.abstraction.UpdatePreferences
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.domain.repositories.BookLocalRepository
import com.lgdevs.mynextbook.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdatePreferencesImpl(
    private val repository: PreferencesRepository
) : UpdatePreferences() {
    override suspend fun execute(params: AppPreferences): Flow<Unit> = flow {
        emit(repository.updatePreferences(params))
    }

}