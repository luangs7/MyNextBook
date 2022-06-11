package com.lgdevs.mynextbook.filter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.interactor.abstraction.GetPreferences
import com.lgdevs.mynextbook.domain.interactor.abstraction.UpdatePreferences
import com.lgdevs.mynextbook.domain.model.AppPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val setPreferences: UpdatePreferences,
    private val getPreferences: GetPreferences
) : ViewModel() {

    private val _preferencesState = MutableStateFlow<ViewState<AppPreferences>>(ViewState.Loading)
    val preferencesState: StateFlow<ViewState<AppPreferences>>
        get() = _preferencesState

    init {
        getPreferences()
    }

    private fun getPreferences() {
        viewModelScope.launch {
            getPreferences.execute(Unit)
                .catch { _preferencesState.value = ViewState.Error(it) }
                .onStart { _preferencesState.value = ViewState.Loading }
                .collect {
                    _preferencesState.value = ViewState.Success(it)
                }
        }
    }

    fun setPreferences(isEbook: Boolean, keyword:String?, isPortuguese: Boolean) {
        viewModelScope.launch {
            val preferences = AppPreferences(isEbook, keyword, "pt", null)
            setPreferences.execute(preferences)
                .catch { _preferencesState.value = ViewState.Error(it) }
                .onStart { _preferencesState.value = ViewState.Loading }
                .collect {
                    _preferencesState.value = ViewState.Success(preferences)
                }
        }
    }
}