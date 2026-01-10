package com.example.test_movie_app.ui.theme.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class Entity(
    @PrimaryKey val id : Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val genreIds: List<Int>
)