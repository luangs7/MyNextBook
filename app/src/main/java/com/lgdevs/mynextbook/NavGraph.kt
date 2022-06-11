package com.lgdevs.mynextbook

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lgdevs.mynextbook.filter.viewmodel.PreferencesViewModel
import com.lgdevs.mynextbook.finder.find.FindView
import com.lgdevs.mynextbook.navigation.Destinations
import com.lgdevs.mynextbook.navigation.NavigationItem
import com.lgdevs.mynextbook.welcome.BookWelcomeView

@ExperimentalFoundationApi
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Welcome.route) {
        composable(NavigationItem.Welcome.route) {
            BookWelcomeView(navController)
        }
        composable(NavigationItem.Finder.route) {
            FindView(navController)
        }
    }
}