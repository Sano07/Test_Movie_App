package com.example.test_movie_app.ui.theme.fav_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.test_movie_app.ui.theme.main_screen.MainScreenBody
import com.example.test_movie_app.ui.theme.main_screen.MainScreenVM
import com.example.test_movie_app.ui.theme.navigation.BottomNavLine

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavScreen(
    viewModel: MainScreenVM,
    selectedItem: MutableState<String>,
    navController: NavController
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    Scaffold(
        modifier = Modifier.zIndex(100f),
        topBar = {},
        bottomBar = {
            BottomNavLine(
                selectedItem = selectedItem,
                navController = navController
            )
        }
    ) { padding ->

        FavScreenBody(
            modifier = Modifier.padding(padding),
            favoriteMovies = favoriteMovies,
            onRemoveClick = { movieEntity ->
                viewModel.deleteFavorite(movieEntity.id)
            }
        )
    }
}