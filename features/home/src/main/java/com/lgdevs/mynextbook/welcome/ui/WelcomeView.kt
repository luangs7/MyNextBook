package com.lgdevs.mynextbook.welcome.ui

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lgdevs.mynextbook.cloudservices.analytics.CloudServicesAnalytics
import com.lgdevs.mynextbook.designsystem.ui.components.customview.InformationView
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.navigation.NavigationItem
import com.lgdevs.mynextbook.welcome.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun BookWelcomeView(navController: NavController) {
    val cloudServices = get<CloudServicesAnalytics>()
    val scope = rememberCoroutineScope()
    val bundle = Bundle()
    bundle.putString("TESTEEE", "123212")
    MyNextBookTheme {
        InformationView(
            stringResource(R.string.welcome_title),
            stringResource(R.string.welcome_subtitle),
            stringResource(R.string.btn_go),
            R.raw.book_idea,
            onNext = {
                scope.launch {
                    cloudServices.onEvent("aaaa", bundle)
                }

                navController.navigate(NavigationItem.Finder.route)
            },
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun showPreview() {
    BookWelcomeView(rememberNavController())
}
