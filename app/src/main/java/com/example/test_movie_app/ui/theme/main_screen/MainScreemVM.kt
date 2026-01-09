package com.example.test_movie_app.ui.theme.main_screen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.test_movie_app.ui.theme.data.MovieDataModel
import org.json.JSONObject

class MainScreenVM : ViewModel() {

    val movieList = mutableStateListOf<MovieDataModel>()

    fun loadMovies(context: Context) {
        apiCall(context)
    }

    private fun apiCall(context: Context) {
        val API_KEY = "9dbc4f311e85cd974cbff94a32ce5657"
        val url =  "https://api.themoviedb.org/3/movie/popular?api_key=$API_KEY"

        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->

                val jsonObject = JSONObject(response)
                val resultsArray = jsonObject.getJSONArray("movies")

                for (i in 0 until resultsArray.length()) {

                    val movieObject = resultsArray.getJSONObject(i)

                    val id = movieObject.getInt("id")
                    val title = movieObject.getString("title")
                    val overview = movieObject.getString("overview")
                    val releaseDate = movieObject.getString("release_date")
                    val posterPath = movieObject.getString("poster_path")

                    val genreJsonArray = movieObject.getJSONArray("genre_ids")
                    val genreIds = mutableListOf<Int>()

                    for (j in 0 until genreJsonArray.length()) {
                        genreIds.add(genreJsonArray.getInt(j))
                    }

                    val movie = MovieDataModel(
                        id = id,
                        title = title,
                        posterPath = posterPath,
                        overview = overview,
                        releaseDate = releaseDate,
                        genreIds = genreIds
                    )

                    movieList.add(movie)
                }

            },
            {
                Log.d("MyLog", "Error: $it")
            }

        )
        queue.add(request)
    }

}