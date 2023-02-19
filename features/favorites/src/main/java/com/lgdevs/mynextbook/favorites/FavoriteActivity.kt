package com.lgdevs.mynextbook.favorites

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.play.core.splitcompat.SplitCompat
import com.lgdevs.mynextbook.designsystem.ui.components.material.ScaffoldView
import com.lgdevs.mynextbook.designsystem.ui.components.material.TopBar
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.designsystem.ui.theme.backgroundDark
import com.lgdevs.mynextbook.favorites.di.injectDynamicFeature

@OptIn(ExperimentalFoundationApi::class)
class FavoriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDynamicFeature()

        setContent {
            MyNextBookTheme {
                FavoritesMainView()
            }
        }
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        SplitCompat.installActivity(context)
    }

    @Composable
    internal fun FavoritesMainView(){
        ScaffoldView(
            topBar = {
                TopBar(true,
                    navigationIconClick = { onBackPressed() },
                hasAction = false)
            }, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(backgroundDark)
        ) {
            FavoritesView()
        }

    }
}