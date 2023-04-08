package com.lgdevs.mynextbook.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.lgdevs.mynextbook.designsystem.ui.components.actionbutton.ActionButtonsContainer
import com.lgdevs.mynextbook.designsystem.ui.theme.listTitleStyle
import com.lgdevs.mynextbook.domain.model.Book

@Composable
fun FavoritesItem(
    book: Book,
    onFavorite: (Book) -> Unit,
    onPreview: (Book) -> Unit,
    modifier: Modifier = Modifier,
) {
    val favColor by remember { mutableStateOf(Color.Red) }

    Column(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)){
        Image(
            painter = rememberAsyncImagePainter(book.imageLinks?.thumbnail),
            contentDescription = String(),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(100.dp)
                .shadow(10.dp, shape = RoundedCornerShape(8.dp))
                .height(160.dp),
        )
        Text(text = book.title.orEmpty(),
            style = listTitleStyle,
            modifier = Modifier.padding(6.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis)
        ActionButtonsContainer(
            onFavorite = { onFavorite(book) },
            onPreview = { onPreview(book) },
            modifier = modifier
                .padding(bottom = 8.dp),
            mutableFavColor = favColor,
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun showItem() {
    FavoritesItem(Book("123"), onFavorite = {}, onPreview = {})
}