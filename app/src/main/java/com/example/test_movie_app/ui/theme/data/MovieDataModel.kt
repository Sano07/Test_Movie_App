package com.example.test_movie_app.ui.theme.data

data class MovieDataModel(
    val id : Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val genreIds: List<Int>
)