package com.lgdevs.mynextbook.domain.interactor.implementation

import com.lgdevs.mynextbook.domain.model.AppPreferences
import kotlinx.coroutines.flow.Flow

fun interface UpdatePreferencesUseCase: OnUpdatePreferences

typealias OnUpdatePreferences = suspend (AppPreferences) -> Unit