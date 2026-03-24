package com.example.test_movie_app.ui.theme.room

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromGenreIdsList(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toGenreIdsList(data: String): List<Int> {
        if (data.isEmpty()) return emptyList()
        return data.split(",").map { it.toInt() }
    }
}