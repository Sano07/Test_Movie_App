package com.example.test_movie_app.ui.theme.desc_screen

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.test_movie_app.ui.theme.fav_screen.FavScreenBody
import com.example.test_movie_app.ui.theme.navigation.BottomNavLine

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DescScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.zIndex(100f),
        topBar = {},
        bottomBar = {
            BottomNavLine(
                selectedItem,
                navController = navController
            )
        }
    ) {  padding ->

        FavScreenBody()
    }
}