package com.example.test_movie_app.ui.theme.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoMovies {
    @Query("SELECT * FROM favorite_movies")
    fun getAllMovies() : Flow<List<Entity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteMovie: List<Entity>)

    @Delete
    suspend fun delete(favoriteMovie : Entity)
}