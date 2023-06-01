package com.lgdevs.mynextbook.designsystem.ui.components.material

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.lgdevs.mynextbook.designsystem.ui.theme.backgroundDark
import kotlinx.coroutines.launch

typealias OnLogoutClickListener = () -> Unit
typealias OnNavigationIconClickListener = () -> Unit
typealias OnFavoritesClickListener = () -> Unit

@Composable
fun AppView(
    hasBack: Boolean,
    hasAction: Boolean,
    OnLogoutClickListener: OnLogoutClickListener,
    OnNavigationIconClickListener: OnNavigationIconClickListener,
    OnFavoritesClickListener: OnFavoritesClickListener,
    content: @Composable () -> Unit
){
    ScaffoldView(
        topBar = {
            TopBar(
                hasBack= hasBack,
                navigationIconClick = { OnNavigationIconClickListener.invoke() },
                actionClick = {
                    when(it) {
                        TopBarAction.LOGOUT -> OnLogoutClickListener.invoke()
                        TopBarAction.FAVORITES -> OnFavoritesClickListener.invoke()
                    }
                },
                hasAction = hasAction
            )
        }, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(backgroundDark)
    ) {
        content()
    }
}