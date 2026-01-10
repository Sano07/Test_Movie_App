package com.example.test_movie_app.ui.theme.room

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun Dao() : Dao

}