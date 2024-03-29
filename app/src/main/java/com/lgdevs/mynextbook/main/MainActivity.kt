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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lgdevs.mynextbook.designsystem.ui.components.material.AppView
import com.lgdevs.mynextbook.designsystem.ui.components.material.ScaffoldView
import com.lgdevs.mynextbook.designsystem.ui.components.material.TopBar
import com.lgdevs.mynextbook.designsystem.ui.components.material.TopBarAction
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.designsystem.ui.theme.backgroundDark
import com.lgdevs.mynextbook.di.LOGOUT_OBSERVER_QUALIFIER
import com.lgdevs.mynextbook.navigation.NavGraph
import com.lgdevs.mynextbook.navigation.NavigationItem
import com.lgdevs.mynextbook.observer.KObserver
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.get
import org.koin.androidx.compose.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

typealias DoLogout = suspend () -> Unit

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSplash()
        setContent {
            MyNextBookTheme {
                MainView(
                    doLogoutListener = {
                        viewModel.logout()
                    }
                )
            }
        }
    }

    private fun setupSplash() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun MainView(
    doLogoutListener: DoLogout,
    logoutObserver: KObserver<Unit> = get(LOGOUT_OBSERVER_QUALIFIER)
) {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val route = currentBackStackEntry.value?.destination?.route ?: ""
    val hasBack = when {
        route.contains(NavigationItem.Welcome.route) ||
                route.contains(NavigationItem.Login.route) -> false
        else -> true
    }
    val scope = rememberCoroutineScope()
    val hasAction = route.contains(NavigationItem.Login.route).not()


    AppView(
        hasBack= hasBack,
        hasAction= hasAction,
        OnFavoritesClickListener = { navController.navigate(NavigationItem.Favorites.route) },
        OnNavigationIconClickListener = { navController.popBackStack() },
        OnLogoutClickListener = {
            scope.launch {
                doLogoutListener.invoke()
            }
        },
        content = {
            NavGraph(navController = navController)
        }
    )

    logoutObserver.observer {
        navController.navigate(NavigationItem.Login.route)
    }
}