package com.lgdevs.mynextbook.finder.preview.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.designsystem.ui.components.actionbutton.ActionButtonsContainer
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
    val bookFavoriteState = viewModel.bookFavoriteFlow.collectAsState(initial = ViewState.Empty)
    var favColor by remember { mutableStateOf(if (bookState.value.isFavorited) Color.Red else Color.White) }

    ActionButtonsContainer(
        onFavorite = { onFavorite(bookState.value) },
        onPreview = { onPreview(bookState.value) },
        onShare = { onShare(bookState.value) },
        modifier = modifier,
        mutableFavColor = favColor
    )

    LaunchedEffect(bookFavoriteState.value){
        when (bookFavoriteState.value) {
            is ViewState.Success -> {
                if(bookState.value.isFavorited){
                    bookState.value = bookState.value.copy(
                        isFavorited = false
                    )
                    favColor = Color.White
                } else {
                    bookState.value = bookState.value.copy(
                        isFavorited = true
                    )
                    favColor = Color.Red
                }

            }
            else -> {}
        }
    }


}


