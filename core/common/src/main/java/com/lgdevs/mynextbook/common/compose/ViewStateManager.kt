package com.lgdevs.mynextbook.common.compose

import androidx.compose.runtime.Composable
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.LoadingView
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.StatusView
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.model.ViewStateParam

@Composable
fun <T> onViewState(
    state: ViewState<T>,
    loadingState: ViewStateParam? = null,
    emptyState: ViewStateParam? = null,
    errorState: ViewStateParam? = null,
    onEach: @Composable ((ViewState<T>) -> Unit)? = null,
    content: @Composable (T?) -> Unit
) {
    onEach?.invoke(state)
    when (state) {
        ViewState.Empty -> emptyState?.let { StatusView(it) }
        is ViewState.Error -> errorState?.let { StatusView(it) }
        ViewState.Loading -> loadingState?.let { LoadingView(it) }
        is ViewState.Success -> content(state.result)
    }
}