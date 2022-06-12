package com.lgdevs.mynextbook.designsystem.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieView(
    resId: Int,
    modifier: Modifier = Modifier
) {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
    LottieAnimation(
        composition.value,
        modifier = modifier.size(220.dp),
        iterations = LottieConstants.IterateForever
    )
}