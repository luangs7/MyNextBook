package com.lgdevs.mynextbook.designsystem.ui.components.stateview

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lgdevs.mynextbook.designsystem.ui.components.lottie.LottieView
import com.lgdevs.mynextbook.designsystem.ui.components.stateview.model.ViewStateParam

@Composable
fun LoadingView(stateParam: ViewStateParam) {
    LottieView(
        resId = stateParam.animation,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}