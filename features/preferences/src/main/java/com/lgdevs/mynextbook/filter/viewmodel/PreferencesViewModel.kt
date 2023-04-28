package com.lgdevs.mynextbook.filter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.implementation.GetPreferencesUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.GetUserUseCase
import com.lgdevs.mynextbook.domain.interactor.implementation.UpdatePreferencesUseCase
import com.lgdevs.mynextbook.domain.model.AppPreferences
import com.lgdevs.mynextbook.extensions.collectIfSuccess
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class PreferencesViewModel(
    private val setPreferencesUseCase: UpdatePreferencesUseCase,
    private val getPreferencesUseCase: GetPreferencesUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val preferencesSharedFlow: MutableSharedFlow<AppPreferences> =
        MutableSharedFlow(replay = 1)

    private var eventHandled: Boolean = false

    @OptIn(FlowPreview::class)
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
            getUserUseCase().collectIfSuccess { user ->
                setPreferencesUseCase(preferences, user.uuid).run {
                    emit(ViewState.Success(preferences))
                    eventHandled = true
                }
            }
        }.catch { emit(ViewState.Error(it)) }.onStart { emit(ViewState.Loading) }

    fun getPreferences(): Flow<ViewState<AppPreferences>> = flow<ViewState<AppPreferences>> {
        getUserUseCase().collectIfSuccess { user ->
            getPreferencesUseCase(user.uuid).collect {
                emit(ViewState.Success(it))
            }
        }
    }.catch { emit(ViewState.Error(it)) }.onStart { emit(ViewState.Loading) }
}
