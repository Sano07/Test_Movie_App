package com.example.test_movie_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.test_movie_app.ui.theme.Test_Movie_AppTheme
import com.example.test_movie_app.ui.theme.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var favMovie by remember { mutableStateOf(setOf<Int>()) }

            AppNavigation(
                favMovie ,
                onFavMovieUpdate = { favMovie = it },
                onFavMovieChange = { MoviecarId ->
                    favMovie = if (favMovie.contains(MoviecarId)) {
                        favMovie - MoviecarId
                    } else {
                        favMovie + MoviecarId
                    }
                }
            )
        }
    }
}
