package com.lgdevs.mynextbook.finder.preview.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import org.koin.androidx.compose.getViewModel
import kotlin.math.min

@Composable
fun PreviewView(
    navController: NavController,
    viewModel: PreviewViewModel = getViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(backgroundDark)
    ) {
        PreviewViewContent(viewModel)
    }
}

@Composable
internal fun PreviewViewContent(
    viewModel: PreviewViewModel
) {
    val state = viewModel.getRandomBook().collectAsState(ViewState.Loading)

    Crossfade(targetState = state) { viewState ->
        when (val result = viewState.value) {
            ViewState.Loading -> LottieLoadingView()
            is ViewState.Success -> PreviewItem(result.result, onPreview = {}, onFavorite = {})
            else -> {}
        }
    }
}

@Composable
internal fun PreviewItem(
    book: Book,
    onFavorite: () -> Unit,
    onPreview: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = rememberAsyncImagePainter(book.imageLinks?.thumbnail),
            contentDescription = String(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .height(300.dp)
                .graphicsLayer {
                    alpha = min(1f, 1 - (scrollState.value / 600f))
                    translationY = -scrollState.value * 0.1f
                }
        )
        Text(
            text = book.title.orEmpty(),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Text(
            text = book.description.orEmpty(),
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )

        BookActions(
            onFavorite = { onFavorite.invoke() },
            onPreview = { onPreview.invoke() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
internal fun BookActions(
    onFavorite: () -> Unit,
    onPreview: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = { onFavorite.invoke() },
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            Text(text = "Favoritar", style = MaterialTheme.typography.body1)
        }
        OutlinedButton(
            onClick = { onPreview.invoke() },
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            Text(
                text = "Ver preview",
                style = MaterialTheme.typography.body1
            )
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