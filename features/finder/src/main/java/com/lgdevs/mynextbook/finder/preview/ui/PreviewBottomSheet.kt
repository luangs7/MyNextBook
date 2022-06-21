package com.lgdevs.mynextbook.finder.preview.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.lgdevs.mynextbook.domain.model.Book

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PreviewBottomSheet(
    book: Book,
    onFavorite: (book: Book) -> Unit,
    onPreview: (book: Book) -> Unit,
    onShare: (book: Book) -> Unit
) {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val sheetPeekHeight = (LocalConfiguration.current.screenHeightDp * 0.35).dp

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp),
        sheetPeekHeight = sheetPeekHeight,
        sheetBackgroundColor = Color.White,
        sheetContent = {
            PreviewItem(book)
        }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val (preview, actions) = createRefs()
            val guidelineTop = createGuidelineFromTop(0.5f)
            Image(
                painter = rememberAsyncImagePainter(book.imageLinks?.thumbnail),
                contentDescription = String(),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(220.dp)
                    .padding(top = 16.dp)
                    .shadow(10.dp, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .constrainAs(preview) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(guidelineTop)
                        height = Dimension.fillToConstraints
                    }
            )
            BookActions(
                book = book,
                onFavorite = onFavorite,
                onPreview = onPreview,
                onShare = onShare,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .constrainAs(actions) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(guidelineTop)
                    }
            )
        }
    }
}