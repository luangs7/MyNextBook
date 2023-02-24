@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package com.lgdevs.mynextbook.login.ui

import androidx.compose.animation.Crossfade
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lgdevs.mynextbook.common.base.ViewState
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.login.viewmodel.LoginViewModel
import com.lgdevs.mynextbook.navigation.NavigationItem
import org.koin.androidx.compose.getViewModel

typealias OnLoginSuccess = () -> Unit
typealias OnLoginError = () -> Unit
typealias OnLoginLoading = (Boolean) -> Unit
@Composable
fun LoginView(
    navController: NavController,
    viewModel: LoginViewModel = getViewModel()
) {
    val userState = viewModel.userFlow.collectAsState(initial = ViewState.Loading)
    var hasRedirected by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) { viewModel.getUser() }
    Crossfade(targetState = userState) { state ->
        when (state.value) {
            ViewState.Empty,
            is ViewState.Error -> {
                Login(navController, viewModel)
            }
            is ViewState.Success -> if (hasRedirected.not()) {
                navController.navigate(NavigationItem.Welcome.route)
                hasRedirected = true
            }
            else -> {}
        }
    }
}

@Composable
private fun Login(
    navController: NavController,
    viewModel: LoginViewModel
) {
    viewModel.onGetEmail().collectAsState(initial = null).value?.let {
        MyNextBookTheme {
            LoginContent(navController = navController, viewModel, it)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LoginPreview() {
    LoginView(navController = rememberNavController())
}