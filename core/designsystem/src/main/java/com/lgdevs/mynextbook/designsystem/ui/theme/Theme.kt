package com.lgdevs.mynextbook.designsystem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = backgroundDark,
    onBackground = backgroundDark,
    surface = backgroundDark,
    onSurface = backgroundDark,
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = backgroundDark,
    onBackground = backgroundDark,
    surface = backgroundDark,
    onSurface = backgroundDark,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyNextBookTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}


@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    toolbarTitle: String = "",
    hasBack: Boolean = false,
    isMenu: Boolean = false,
    navigationIconClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = toolbarTitle
            )
        },
        navigationIcon = {
            when  {
                hasBack -> {
                    IconButton(onClick = { navigationIconClick?.invoke() }) {
                        Icon(Icons.Filled.ArrowBack, "")
                    }
                }
                isMenu -> {
                    IconButton(onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, "")
                    }
                }
            }
        }
    )
}
