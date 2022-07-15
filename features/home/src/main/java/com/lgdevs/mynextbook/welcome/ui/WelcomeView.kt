package com.lgdevs.mynextbook.welcome.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lgdevs.mynextbook.designsystem.ui.components.customview.InformationView
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.navigation.NavigationItem
import com.lgdevs.mynextbook.welcome.R

@Composable
fun BookWelcomeView(navController: NavController) {
    MyNextBookTheme {
        InformationView(
            stringResource(R.string.welcome_title),
            stringResource(R.string.welcome_subtitle),
            stringResource(R.string.btn_go),
            R.raw.book_idea,
            onNext = { navController.navigate(NavigationItem.Finder.route) }
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun showPreview() {
    BookWelcomeView(rememberNavController())
}