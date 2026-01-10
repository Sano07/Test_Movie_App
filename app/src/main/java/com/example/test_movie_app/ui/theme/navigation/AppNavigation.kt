package com.example.test_movie_app.ui.theme.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test_movie_app.ui.theme.data.MovieDataModel
import com.example.test_movie_app.ui.theme.fav_screen.FavScreen
import com.example.test_movie_app.ui.theme.main_screen.MainScreen
import com.example.test_movie_app.ui.theme.desc_screen.DescScreen

@SuppressLint("UnrememberedMutableState")
@Composable
fun AppNavigation(
    favMovie: Set<Int>,
    onFavMovieUpdate: (Set<Int>) -> Unit,
    onFavMovieChange: (Int) -> Unit,
) {

    val navController = rememberNavController()
    val selectedItem = remember { mutableStateOf("home_screen") }
    val selectedMovieForDesc = remember { mutableStateOf<Int?>(null) }

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            MainScreen(favMovie, selectedItem, navController, onFavMovieUpdate, onFavMovieChange, selectedMovieForDesc)
        }
        composable("favorite_screen") {
            FavScreen(selectedItem, onFavMovieChange, navController)
        }
        composable("desc_screen") {
            DescScreen(navController)
        }
    }
}
