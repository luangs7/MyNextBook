package com.lgdevs.mynextbook.filter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetPreferences
import com.lgdevs.mynextbook.domain.interactor.abstraction.UpdatePreferences
import com.lgdevs.mynextbook.domain.model.AppPreferences
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val setPreferences: UpdatePreferences,
    private val getPreferences: GetPreferences
) : ViewModel() {

    private val preferencesSharedFlow = MutableSharedFlow<AppPreferences>(replay = 1)

    val addPreferences = preferencesSharedFlow.flatMapMerge {
        onSetPreferences(it)
    }

    fun getPreferences(): Flow<ViewState<AppPreferences>> = flow {
        getPreferences.execute(Unit)
            .catch { emit(ViewState.Error(it)) }
            .onStart { emit(ViewState.Loading) }
            .collect {
                emit(ViewState.Success(it))
            }
    }

    fun setPreferences(isEbook: Boolean, keyword: String?, isPortuguese: Boolean) =
        preferencesSharedFlow.tryEmit(AppPreferences(isEbook, keyword, isPortuguese, null))

    private fun onSetPreferences(preferences: AppPreferences) =
        flow<ViewState<AppPreferences>> {
            setPreferences.execute(preferences)
                .catch { emit(ViewState.Error(it)) }
                .onStart { emit(ViewState.Loading) }
                .collect {
                    emit(ViewState.Success(preferences))
                }
        }
}