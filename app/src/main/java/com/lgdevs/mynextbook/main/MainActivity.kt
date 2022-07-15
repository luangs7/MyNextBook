package com.lgdevs.mynextbook.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lgdevs.mynextbook.navigation.NavGraph
import com.lgdevs.mynextbook.designsystem.ui.components.material.ScaffoldView
import com.lgdevs.mynextbook.designsystem.ui.components.material.TopBar
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.designsystem.ui.theme.backgroundDark
import com.lgdevs.mynextbook.navigation.NavigationItem

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply{
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        setContent {
            MyNextBookTheme {
                MainView()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MainView() {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val hasBack =
        currentBackStackEntry.value?.destination?.route?.contains(NavigationItem.Welcome.route)
            ?.not() ?: true

    ScaffoldView(
        topBar = {
            TopBar(hasBack,
                navigationIconClick = { navController.popBackStack() },
                actionClick = { navController.navigate(NavigationItem.Favorites.route) }
            )
        }, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(backgroundDark)
    ) {
        NavGraph(navController = navController)
    }

}