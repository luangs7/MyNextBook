package com.lgdevs.mynextbook.finder.preview.ui

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.common.compose.onViewState
import com.lgdevs.mynextbook.common.helper.shareIntent
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.model.ViewStateParam
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.finder.R
import com.lgdevs.mynextbook.finder.preview.viewmodel.PreviewViewModel
import org.koin.androidx.compose.getViewModel
import com.lgdevs.mynextbook.common.R as commonR

@Composable
fun PreviewView() {
    PreviewViewContent()
}

@Composable
internal fun PreviewViewContent(
    viewModel: PreviewViewModel = getViewModel(),
) {
    val bookState = viewModel.randomBookFlow.collectAsState(ViewState.Loading)

    LaunchedEffect(key1 = Unit) { viewModel.randomBook() }

    Crossfade(targetState = bookState) { viewState ->
        PreviewHandleViewStates(viewState.value)
    }
}

@Composable
internal fun PreviewHandleViewStates(
    state: ViewState<Book>,
    viewModel: PreviewViewModel = getViewModel(),
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    onViewState(
        state = state,
        loadingState = ViewStateParam(R.raw.book_search),
        errorState = ViewStateParam(
            commonR.raw.lottie_error,
            stringResource(id = commonR.string.error_message),
        ),
        emptyState = ViewStateParam(
            commonR.raw.lottie_empty,
            stringResource(id = commonR.string.empty_message),
        ),
    ) { data ->
        data?.let { book ->
            PreviewBottomSheet(book, onPreview = {
                uriHandler.openUri(it.previewLink.orEmpty())
            }, onFavorite = {
                viewModel.itemFavoriteBook(it)
            }, onShare = {
                shareIntent(context, it.previewLink.orEmpty())
            })
        }
    }
}
