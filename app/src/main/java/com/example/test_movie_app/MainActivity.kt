package com.example.test_movie_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.test_movie_app.ui.theme.navigation.AppNavigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.test_movie_app.ui.theme.main_screen.MainScreenVM
import com.example.test_movie_app.ui.theme.main_screen.MainScreenVMFactory
import com.example.test_movie_app.ui.theme.room.MovieDatabase

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainScreenVM = viewModel(
                factory = MainScreenVMFactory(database.daoMovies())
            )

            AppNavigation(viewModel = viewModel)
        }
    }
}
