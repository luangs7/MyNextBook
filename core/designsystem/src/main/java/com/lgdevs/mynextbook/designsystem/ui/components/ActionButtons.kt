package com.lgdevs.mynextbook.designsystem.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ActionButtons(
    onFavorite: () -> Unit,
    onPreview: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier,
    mutableFavColor: Color
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ActionButton(
            onClick = { onFavorite() },
            icon = Icons.Filled.Favorite,
            contentColor = mutableFavColor
        )
        ActionButton(
            onClick = { onShare() },
            icon = Icons.Filled.Share,
            contentColor = Color.White
        )
        ActionButton(
            onClick = { onPreview() },
            icon = Icons.Default.Info,
            contentColor = Color.White
        )
    }
}