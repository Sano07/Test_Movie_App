package com.example.test_movie_app.ui.theme.api

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.test_movie_app.ui.theme.data.MovieDataModel
import org.json.JSONObject



private fun apiCall(context: Context) {
    val url =  "https://api.themoviedb.org/3/movie/popular?api_key=$API_KEY"

    val movieList = mutableListOf<MovieDataModel>()
    val queue = Volley.newRequestQueue(context)
    val request = StringRequest(
        Request.Method.GET,
        url,
        { response ->

            val jsonObject = JSONObject(response)
            val resultsArray = jsonObject.getJSONArray("movies")

            for (i in 0 until resultsArray.length()) {

                val movieObject = resultsArray.getJSONObject(i)

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
                    title = title,
                    posterPath = posterPath,
                    overview = overview,
                    releaseDate = releaseDate,
                    conditionIcon = posterPath,
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