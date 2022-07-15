package com.lgdevs.mynextbook.designsystem.ui.components.actionbutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ActionButtonsContainer(
    onFavorite: (() -> Unit)? = null,
    onPreview: (() -> Unit)? = null,
    onShare: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    mutableFavColor: Color
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        onFavorite?.let {
            ActionButton(
                onClick = { it.invoke() },
                icon = Icons.Filled.Favorite,
                contentColor = mutableFavColor,
                borderColor = mutableFavColor
            )
        }
        onShare?.let {
            ActionButton(
                onClick = { it.invoke() },
                icon = Icons.Filled.Share,
                contentColor = Color.White
            )
        }

        onPreview?.let {
            ActionButton(
                onClick = { it.invoke() },
                icon = Icons.Default.ExitToApp,
                contentColor = Color.White
            )
        }
    }
}