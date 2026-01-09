package com.example.test_movie_app.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test_movie_app.ui.theme.fav_screen.FavScreen
import com.example.test_movie_app.ui.theme.main_screen.MainScreen
import com.example.test_movie_app.ui.theme.desc_screen.DescScreen

@Composable
fun AppNavigation(
    favMovie: Set<Int>,
    onFavMovieUpdate: (Set<Int>) -> Unit,
    onFavMovieChange: (Int) -> Unit,
) {

    val navController = rememberNavController()
    val selectedItem = remember { mutableStateOf("main") }
    val selectedMovieForDesc = remember { mutableStateOf<Int?>(null) }

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(favMovie, selectedItem, navController, onFavMovieUpdate, onFavMovieChange, selectedMovieForDesc)
        }
        composable("fav_screen") {
            FavScreen(selectedItem, navController)
        }
        composable("desc_screen") {
            DescScreen(navController)
        }
    }
}
