package com.lgdevs.mynextbook.designsystem.ui.components.stateview

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lgdevs.mynextbook.designsystem.ui.components.lottie.LottieView
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.model.ViewStateParam
import com.lgdevs.mynextbook.designsystem.ui.theme.listTitleStyle

@Composable
fun StatusView(
    stateParam: ViewStateParam
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieView(
                resId = stateParam.animation,
                modifier = Modifier.size(180.dp),
                iterations = 1
            )
            stateParam.description?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    style = listTitleStyle,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

        }
    }

}