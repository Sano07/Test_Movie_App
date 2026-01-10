package com.example.test_movie_app.ui.theme.main_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.test_movie_app.ui.theme.data.MovieDataModel
import com.example.test_movie_app.ui.theme.room.DaoMovies
import com.example.test_movie_app.ui.theme.room.Entity
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainScreenVM(appDatabase: Database)  : ViewModel() {

    var favFilms by mutableStateOf(setOf<Int>())

    val movieList = mutableStateListOf<MovieDataModel>()
    val favMovie = mutableStateSetOf<Int>()

    fun loadMovies(context: Context) {
        apiCall(context)
    }

    val favoriteMovies: List<MovieDataModel>
        get() = movieList.filter { movie ->
            favMovie.contains(movie.id)
        }

    fun toggleFavorite(movieId: Int, dao: DaoMovies) {
        if (favMovie.contains(movieId)) {
            favMovie.remove(movieId)
        } else {
            favMovie.add(movieId)
        }

        saveFavMoviesToDb(movieList, favMovie, dao)
    }

    fun saveFavMoviesToDb(movieList: List<MovieDataModel>, favMovie: Set<Int>, dao: DaoMovies) {
        // Фильтруем только те фильмы, которые есть в избранном
        val moviesToSave = movieList.filter { favMovie.contains(it.id) }

        // Сохраняем в базу
        viewModelScope.launch {
            dao.insert(moviesToSave.map { movie ->
                Entity(
                    id = movie.id,
                    title = movie.title,
                    posterPath = movie.posterPath,
                    overview = movie.overview,
                    releaseDate = movie.releaseDate,
                    genreIds = movie.genreIds
                )
            })
        }
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
                val resultsArray = jsonObject.getJSONArray("results")

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