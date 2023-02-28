package com.lgdevs.mynextbook.favorites

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.common.compose.onViewState
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.StatusView
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.model.ViewStateParam
import com.lgdevs.mynextbook.designsystem.ui.theme.backgroundDark
import com.lgdevs.mynextbook.domain.model.Book
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import com.lgdevs.mynextbook.common.R as commonR

@Composable
fun FavoritesView(
    viewModel: FavoritesViewModel = getViewModel(),
) {
    val itemState = viewModel.getFavoriteItems().collectAsState(initial = ViewState.Loading)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(backgroundDark),
    ) {
        FavoritesContent(itemState)
    }
}

@Composable
fun FavoritesContent(
    itemState: State<ViewState<List<Book>>>,
    viewModel: FavoritesViewModel = getViewModel(),
) {
    val scope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current
    val emptyParam = ViewStateParam(
        commonR.raw.lottie_empty,
        stringResource(id = R.string.empty_favorites),
    )

    Crossfade(itemState.value) { state ->
        onViewState(
            state = state,
            errorState = ViewStateParam(
                commonR.raw.lottie_error,
                stringResource(id = commonR.string.error_message),
            ),
            emptyState = emptyParam,
        ) { data ->
            data?.let { result ->
                val listState = remember { mutableStateOf(result) }
                if (listState.value.isEmpty()) {
                    StatusView(stateParam = emptyParam)
                } else {
                    FavoritesList(
                        list = listState,
                        onFavorite = {
                            scope.launch {
                                viewModel.removeItem(it)
                                    .onEach { Log.d("RemoveBookFromFavorite::", it.toString()) }
                                    .distinctUntilChanged()
                                    .stateIn(this)
                            }
                        },
                        onPreview = { uriHandler.openUri(it.previewLink.orEmpty()) },
                    )
                }
            }
        }
    }
}

@Composable
internal fun FavoritesList(
    list: MutableState<List<Book>>,
    onFavorite: (Book) -> Unit,
    onPreview: (Book) -> Unit,
) {
    val state = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        state = state,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(list.value.count()) { index ->
            val item = list.value[index]
            FavoritesItem(
                book = item,
                onFavorite = {
                    onFavorite(item)
                    list.value = list.value.toMutableList()
                        .also { mutableList -> mutableList.remove(item) }
                },
                onPreview = { onPreview(item) },
            )
        }
    }
}
