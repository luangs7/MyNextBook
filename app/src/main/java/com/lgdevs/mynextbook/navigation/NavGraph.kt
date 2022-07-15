package com.lgdevs.mynextbook.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lgdevs.mynextbook.common.helper.convertToSafeDynamicFeatureModuleIntent
import com.lgdevs.mynextbook.finder.find.FindView
import com.lgdevs.mynextbook.finder.preview.ui.PreviewView
import com.lgdevs.mynextbook.welcome.ui.BookWelcomeView
import com.lgdevs.splitfeature.LoadFeature

@ExperimentalFoundationApi
@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(navController, startDestination = NavigationItem.Welcome.route) {
        composable(NavigationItem.Welcome.route) {
            BookWelcomeView(navController)
        }
        composable(NavigationItem.Finder.route) {
            FindView(navController)
        }

        composable(NavigationItem.Preview.route) {
            PreviewView()
        }
        composable(NavigationItem.Favorites.route) {
            LoadFeature(
                context = context,
                featureName = NavigationItem.Favorites.name.toLowerCase(Locale.current),
                onDismiss = { navController.popBackStack() }) {
                // https://issuetracker.google.com/issues/183677219
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(FavoritesDeepLink)
                    `package` = context.packageName
                }
                intent.convertToSafeDynamicFeatureModuleIntent(context)
                context.startActivity(intent)
            }
        }
    }

}

private const val FavoritesDeepLink = "app://lgmakers.mynextbook.favorites"

