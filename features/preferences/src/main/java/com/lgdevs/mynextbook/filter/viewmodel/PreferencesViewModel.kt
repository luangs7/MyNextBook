package com.lgdevs.mynextbook.filter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.implementation.GetPreferencesUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.UpdatePreferencesUseCase
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.domain.model.Book
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val setPreferencesUseCase: UpdatePreferencesUseCase,
    private val getPreferencesUseCase: GetPreferencesUseCase
) : ViewModel() {

    private val preferencesSharedFlow: MutableSharedFlow<AppPreferences> =
        MutableSharedFlow(replay = 1)

    private var eventHandled: Boolean = false

    val addPreferences = preferencesSharedFlow.flatMapMerge {
        onSetPreferences(it)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(), replay = 1)

    fun setPreferences(isEbook: Boolean, keyword: String?, isPortuguese: Boolean) {
        eventHandled = false
        preferencesSharedFlow.tryEmit(AppPreferences(isEbook, keyword, isPortuguese, null))
    }

    private fun onSetPreferences(preferences: AppPreferences) =
        flow<ViewState<AppPreferences>> {
            if (eventHandled) return@flow
            setPreferencesUseCase(preferences).run {
                emit(ViewState.Success(preferences))
                eventHandled = true
            }
        }.catch { emit(ViewState.Error(it)) }.onStart { emit(ViewState.Loading) }

    fun getPreferences(): Flow<ViewState<AppPreferences>> = flow<ViewState<AppPreferences>> {
        getPreferencesUseCase().collect {
                emit(ViewState.Success(it))
        }
    }.catch { emit(ViewState.Error(it)) }.onStart { emit(ViewState.Loading) }
}