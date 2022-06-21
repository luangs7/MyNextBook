package com.lgdevs.mynextbook.finder.preview.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.finder.preview.viewmodel.PreviewViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun BookActions(
    book: Book,
    onFavorite: () -> Unit,
    onPreview: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PreviewViewModel = getViewModel()
) {
    val favoriteState = viewModel.addFavoriteBookState.collectAsState()
    val favorited = remember { mutableStateOf(false) }
    val isFavorited = book.isFavorited || favorited.value
    val favColor = if (isFavorited) Color.Red else Color.White

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedButton(
            onClick = { onFavorite.invoke() },
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.White),
            contentPadding = PaddingValues(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = favColor, disabledContentColor = favColor),
            enabled = isFavorited.not()
        ) {
            Icon(Icons.Filled.Favorite, contentDescription = "content description")
        }

        OutlinedButton(
            onClick = { onShare.invoke() },
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.White),
            contentPadding = PaddingValues(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
        ) {
            Icon(Icons.Filled.Share, contentDescription = "content description")
        }

        OutlinedButton(
            onClick = { onPreview.invoke() },
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.White),
            contentPadding = PaddingValues(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
        ) {
            Icon(Icons.Default.Info, contentDescription = "content description")
        }

    }

    when (favoriteState.value) {
        is ViewState.Success -> {
            viewModel.showSnackbar("Item adicionado aos favoritos.")
            favorited.value = true
        }
        else -> {}
    }
}