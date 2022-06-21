package com.lgdevs.mynextbook.finder.preview.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.lgdevs.mynextbook.designsystem.ui.theme.descriptionStyle
import com.lgdevs.mynextbook.designsystem.ui.theme.subtitleStyle
import com.lgdevs.mynextbook.designsystem.ui.theme.titleStyle
import com.lgdevs.mynextbook.domain.model.Book
import kotlin.math.min

@Composable
internal fun PreviewItem(
    book: Book
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(end = 16.dp, start = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = book.title.orEmpty(),
            style = titleStyle,
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Text(
            text = book.authors?.first().orEmpty(),
            style = subtitleStyle,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Text(
            text = book.description.orEmpty(),
            style = descriptionStyle,
            modifier = Modifier
                .padding(top = 32.dp, bottom = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}