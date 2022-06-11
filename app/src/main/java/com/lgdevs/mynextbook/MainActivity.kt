package com.lgdevs.mynextbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.rememberNavController
import com.lgdevs.mynextbook.designsystem.ui.theme.MyNextBookTheme

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNextBookTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}