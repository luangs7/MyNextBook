package com.lgdevs.mynextbook.finder.preview.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.designsystem.ui.components.LottieView
import com.lgdevs.mynextbook.designsystem.ui.theme.backgroundDark
import com.lgdevs.mynextbook.domain.model.Book
import com.lgdevs.mynextbook.finder.R
import com.lgdevs.mynextbook.finder.preview.viewmodel.PreviewViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import kotlin.math.min

@Composable
fun PreviewView(
    navController: NavController,
    viewModel: PreviewViewModel = getViewModel()
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarState = viewModel.snackbarState.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(backgroundDark),
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    backgroundColor = Color.Black,
                    snackbarData = data
                )
            }
        }
    ) {
        PreviewViewContent()
    }

    Crossfade(targetState = snackbarState) { snackbarState ->
        if (snackbarState.value.isNotEmpty()) {
            LaunchedEffect(scaffoldState.snackbarHostState) {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = snackbarState.value
                    )
                }
            }
        }
    }
}

@Composable
internal fun PreviewViewContent(
    viewModel: PreviewViewModel = getViewModel()
) {
    val bookState = viewModel.getRandomBook().collectAsState(ViewState.Loading)
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Crossfade(targetState = bookState) { viewState ->
        when (val state = viewState.value) {
            ViewState.Loading -> LottieLoadingView()
            is ViewState.Success -> PreviewBottomSheet(state.result, onPreview = {
                uriHandler.openUri(it.previewLink.orEmpty())
            }, onFavorite = {
                viewModel.addFavoriteBook(it)
            }, onShare = {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Encontrei um livro pra você: ${it.previewLink.orEmpty()}"
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            })
            else -> {}
        }
    }
}

@Composable
fun LottieLoadingView() {
    LottieView(
        resId = R.raw.book_search,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}