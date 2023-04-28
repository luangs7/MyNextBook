package com.lgdevs.mynextbook.designsystem.ui.components.material

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lgdevs.mynextbook.designsystem.ui.theme.backgroundDark

typealias onLogoutClickListener = () -> Unit
typealias onNavigationIconClickListener = () -> Unit
typealias onFavoritesClickListener = () -> Unit

@Composable
fun AppView(
    hasBack: Boolean,
    hasAction: Boolean,
    OnLogoutClickListener: onLogoutClickListener,
    OnNavigationIconClickListener: onNavigationIconClickListener,
    OnFavoritesClickListener: onFavoritesClickListener,
    content: @Composable () -> Unit,
) {
    ScaffoldView(
        topBar = {
            TopBar(
                hasBack = hasBack,
                navigationIconClick = { OnNavigationIconClickListener.invoke() },
                actionClick = {
                    when (it) {
                        TopBarAction.LOGOUT -> OnLogoutClickListener.invoke()
                        TopBarAction.FAVORITES -> OnFavoritesClickListener.invoke()
                    }
                },
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
