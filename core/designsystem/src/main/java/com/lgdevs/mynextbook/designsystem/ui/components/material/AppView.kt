package com.lgdevs.mynextbook.designsystem.ui.components.material

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lgdevs.mynextbook.designsystem.ui.theme.backgroundDark

typealias OnNavigationIconClickListener = () -> Unit
typealias OnActionClickListener = (TopBarAction) -> Unit

@Composable
fun AppView(
    hasBack: Boolean,
    hasAction: Boolean,
    onActionClickListener: OnActionClickListener,
    onNavigationIconClickListener: OnNavigationIconClickListener,
    content: @Composable () -> Unit,
) {
    ScaffoldView(
        topBar = {
            TopBar(
                hasBack = hasBack,
                navigationIconClick = { onNavigationIconClickListener.invoke() },
                actionClick = onActionClickListener,
                hasAction = hasAction,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(backgroundDark),
    ) {
        content()
    }
}
