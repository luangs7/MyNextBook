package com.lgdevs.mynextbook

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.lgdevs.mynextbook.finder.find.FindView
import com.lgdevs.mynextbook.finder.preview.ui.PreviewView
import com.lgdevs.mynextbook.navigation.NavigationItem
import com.lgdevs.mynextbook.welcome.ui.BookWelcomeView

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

        composable(NavigationItem.Preview.route) {
            PreviewView(navController)
        }
    }
}