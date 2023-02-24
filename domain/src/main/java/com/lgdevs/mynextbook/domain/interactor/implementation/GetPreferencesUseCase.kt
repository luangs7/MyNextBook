package com.lgdevs.mynextbook.domain.interactor.implementation

import com.lgdevs.mynextbook.domain.model.AppPreferences
import kotlinx.coroutines.flow.Flow

fun interface GetPreferencesUseCase: OnGetPreferences

typealias OnGetPreferences = suspend (String) -> Flow<AppPreferences>