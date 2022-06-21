package com.lgdevs.mynextbook.finder.find

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lgdevs.mynextbook.designsystem.ui.components.InformationView
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.filter.ui.PreferencesView
import com.lgdevs.mynextbook.finder.R
import com.lgdevs.mynextbook.navigation.NavigationItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun FindView(navController: NavController) {
    MyNextBookTheme {
        var isPreferencesOpen by remember { mutableStateOf(false) }

        InformationView(
            stringResource(R.string.information_filter_title),
            stringResource(R.string.information_filter_subtitle),
            stringResource(R.string.btn_search),
            R.raw.book_preferences,
            onInformation = { isPreferencesOpen = true },
            onNext = { navController.navigate(NavigationItem.Preview.route) }
        )

        if (isPreferencesOpen) {
            PreferencesView(onDismiss = { isPreferencesOpen = false })
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun showView() {
    FindView(rememberNavController())
}