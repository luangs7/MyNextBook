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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.finder.preview.viewmodel.PreviewViewModel
import org.koin.androidx.compose.getViewModel

@Composable
internal fun BookActions(
    book: Book,
    onFavorite: (Book) -> Unit,
    onPreview: (Book) -> Unit,
    onShare: (Book) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PreviewViewModel = getViewModel()
) {
    val bookState = remember { mutableStateOf(book) }
    val addFavoriteState = viewModel.addFavoriteBookFlow.collectAsState(initial = null)
    val removeFavoriteState = viewModel.removeFavoriteBookFlow.collectAsState(initial = null)
    val favColor = if (bookState.value.isFavorited) Color.Red else Color.White

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ActionButton(
            onClick = { onFavorite.invoke(bookState.value) },
            icon = Icons.Filled.Favorite,
            contentColor = favColor
        )
        ActionButton(
            onClick = { onShare.invoke(bookState.value) },
            icon = Icons.Filled.Share,
            contentColor = Color.White
        )
        ActionButton(
            onClick = { onPreview.invoke(bookState.value) },
            icon = Icons.Default.Info,
            contentColor = Color.White
        )
    }

    when (addFavoriteState.value) {
        is ViewState.Success -> {
            bookState.value.isFavorited = true
            viewModel.showSnackbar("Item adicionado aos favoritos.")
        }
        else -> {}
    }

    when (removeFavoriteState.value) {
        is ViewState.Success -> {
            bookState.value.isFavorited = false
            viewModel.showSnackbar("Item removido dos favoritos.")
        }
        else -> {}
    }
}


@Composable
fun ActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentColor: Color
) {
    OutlinedButton(
        onClick = { onClick.invoke() },
        modifier = modifier
            .size(50.dp)
            .padding(8.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.White),
        contentPadding = PaddingValues(4.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = contentColor)
    ) {
        Icon(icon, contentDescription = String())
    }
}