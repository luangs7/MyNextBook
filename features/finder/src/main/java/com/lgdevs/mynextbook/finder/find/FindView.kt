package com.lgdevs.mynextbook.finder.find

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lgdevs.mynextbook.designsystem.ui.components.InformationView
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.filter.ui.PreferencesView
import com.lgdevs.mynextbook.finder.R
import com.lgdevs.mynextbook.navigation.NavigationItem

@Composable
fun FindView(navController: NavController) {
    MyNextBookTheme {
        val isPreferencesOpen = remember { mutableStateOf(false) }

        InformationView(
            "Você pode utilizar alguns filtros para ajudar na nossa busca.",
            "Basta clicar aqui para alterar suas preferencias.",
            "Buscar",
            R.raw.book_preferences,
            onInformation = { isPreferencesOpen.value = true },
            onNext = { navController.navigate(NavigationItem.Preview.route) }
        )

        if (isPreferencesOpen.value) {
            PreferencesView(onDismiss = { isPreferencesOpen.value = false })
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun showView() {
    FindView(rememberNavController())
}