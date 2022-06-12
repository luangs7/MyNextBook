package com.lgdevs.mynextbook.welcome.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.lgdevs.mynextbook.designsystem.ui.components.InformationView
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.navigation.NavigationItem
import com.lgdevs.mynextbook.welcome.R

@Composable
fun BookWelcomeView(navController: NavController) {
    MyNextBookTheme {
        InformationView(
            "Está com dúvida de qual vai ser seu próximo livro?",
            "Deixa que vamos te ajudar!",
            "Vamos lá!",
            R.raw.book_idea,
            onNext = { navController.navigate(NavigationItem.Finder.route) }
        )
    }
}

@Composable
@Preview(showBackground = true)
internal fun showPreview() {
    InformationView(
        "Está com dúvida de qual vai ser seu próximo livro?",
        "Deixa que vamos te ajudar!",
        "Vamos lá!",
        R.raw.book_idea
    ) { }
}