package com.example.test_movie_app.ui.theme.desc_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.test_movie_app.ui.theme.fav_screen.FavScreenBody
import com.example.test_movie_app.ui.theme.main_screen.MainScreenVM
import com.example.test_movie_app.ui.theme.navigation.BottomNavLine

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DescScreen(
    viewModel: MainScreenVM,
    selectedMovieForDesc: MutableState<Int?>
) {
    val selectedMovie = viewModel.movieList.find { it.id == selectedMovieForDesc.value }

    Scaffold(
    ) { padding ->
        if (selectedMovie != null) {
            DescScreenBody(
                modifier = Modifier.padding(padding),
                movie = selectedMovie
            )
        }
    }
}