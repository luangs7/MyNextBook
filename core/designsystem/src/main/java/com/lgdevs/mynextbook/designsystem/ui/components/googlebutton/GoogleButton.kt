package com.lgdevs.mynextbook.designsystem.ui.components.googlebutton

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lgdevs.mynextbook.designsystem.R
import com.lgdevs.mynextbook.designsystem.ui.theme.Shapes

@ExperimentalMaterialApi
@Composable
fun GoogleButton(
    text: String = "Entrar com a conta Google",
    icon: Painter = painterResource(R.drawable.ic_google_logo),
    shape: CornerBasedShape = Shapes.medium,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = Color.White,
    textColor: Color = Color.Black,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    val clicked = remember { mutableStateOf(false) }
    Surface(
        onClick = { clicked.value = !clicked.value },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor,
        modifier = modifier

    ) {
        Row(
            Modifier
                .padding( //Da padding en el Row
                    start = 12.dp,
                    end = 12.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = "Google Button",
                tint = Color.Unspecified,

                )
            Spacer(Modifier.width(16.dp))
            Text(text = text, color = textColor)
            if (clicked.value) {
                clicked.value = false
                onClicked()
            }
        }

    }
}

@ExperimentalMaterialApi
@Composable
@Preview
private fun GoogleButtonPreview() {
    GoogleButton(
        text = "Registrate con Google",
        onClicked = {}
    )
}
