package com.example.test_movie_app.ui.theme.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test_movie_app.ui.theme.room.DaoMovies

class MainScreenVMFactory(
    private val dao: DaoMovies
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainScreenVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainScreenVM(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}