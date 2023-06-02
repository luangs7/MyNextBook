package com.lgdevs.mynextbook.designsystem.ui.components.material

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class TopBarAction {
    FAVORITES,
    LOGOUT,
    CHAT
}

@Composable
fun TopBar(
    hasBack: Boolean,
    navigationIconClick: () -> Unit,
    hasAction: Boolean,
    actionClick: ((TopBarAction) -> Unit)? = null
) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        title = {},
        navigationIcon = {
            if (hasBack) {
                IconButton(onClick = { navigationIconClick() }) {
                    Icon(Icons.Filled.ArrowBack, String(), tint = Color.White)
                }
            }
        },
        actions = {
            if(hasAction) {
                actionClick?.let {
                    IconButton(onClick = { it.invoke(TopBarAction.CHAT) }) {
                        Icon(Icons.Outlined.Person, String(), tint = Color.White)
                    }
                    IconButton(onClick = { it.invoke(TopBarAction.FAVORITES) }) {
                        Icon(Icons.Outlined.Favorite, String(), tint = Color.White)
                    }
                    IconButton(onClick = { it.invoke(TopBarAction.LOGOUT) }) {
                        Icon(Icons.Outlined.ExitToApp, String(), tint = Color.White)
                    }
                }
            }
        }
    )
}