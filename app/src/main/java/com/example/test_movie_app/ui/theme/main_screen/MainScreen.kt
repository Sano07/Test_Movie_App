package com.example.test_movie_app.ui.theme.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.test_movie_app.ui.theme.data.MovieDataModel
import com.example.test_movie_app.ui.theme.navigation.BottomNavLine

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    favMovie: Set<Int>,
    selectedItem: MutableState<String>,
    navController: NavController,
    onFavMovieUpdate: (Set<Int>) -> Unit,
    onFavMovieChange: (Int) -> Unit,
    selectedMovieForDesc: MutableState<Int?>
) {
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

        MainScreenBody(modifier = Modifier.padding(padding), favMovie, onFavMovieUpdate,onFavMovieChange, navController, selectedMovieForDesc)
    }
}
