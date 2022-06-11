package com.lgdevs.mynextbook.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.lgdevs.mynextbook.designsystem.ui.components.InformationView
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme
import com.lgdevs.mynextbook.designsystem.ui.theme.backgroundDark
import com.lgdevs.mynextbook.navigation.NavigationItem

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