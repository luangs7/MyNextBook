package com.lgdevs.mynextbook.designsystem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.lgdevs.mynextbook.designsystem.ui.theme.backgroundDark

@Composable
fun InformationView(
    title: String,
    subtitle: String,
    button: String,
    resId: Int,
    onInformation: (() -> Unit)? = null,
    onNext: () -> Unit
) {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .clickable { onInformation?.invoke() }
        .background(backgroundDark)) {
        val guideline = createGuidelineFromTop(0.5f)
        val (lottieGif, titleId, subtitleId, btnNext) = createRefs()

        LottieView(
            resId,
            modifier = Modifier
                .size(220.dp)
                .padding(bottom = 16.dp)
                .constrainAs(lottieGif) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(guideline)
                }
        )
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .constrainAs(titleId) {
                    start.linkTo(parent.start)
                    top.linkTo(guideline)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .constrainAs(subtitleId) {
                    start.linkTo(parent.start)
                    top.linkTo(titleId.bottom)
                    end.linkTo(parent.end)
                },
            textAlign = TextAlign.Center
        )
        Button(onClick = { onNext.invoke() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .constrainAs(btnNext) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
            Text(
                text = button,
                style = MaterialTheme.typography.body1
            )
        }

    }
}