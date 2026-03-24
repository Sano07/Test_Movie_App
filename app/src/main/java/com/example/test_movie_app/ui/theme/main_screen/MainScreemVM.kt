package com.example.test_movie_app.ui.theme.main_screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.test_movie_app.ui.theme.data.MovieDataModel
import com.example.test_movie_app.ui.theme.room.DaoMovies
import com.example.test_movie_app.ui.theme.room.MovieEntity
import kotlinx.coroutines.launch
import org.json.JSONObject

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenVM(
    private val dao: DaoMovies
) : ViewModel() {

    val movieList = mutableStateListOf<MovieDataModel>()

    private val _favoriteMovies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val favoriteMovies: StateFlow<List<MovieEntity>> = _favoriteMovies.asStateFlow()

    var favoriteIds by mutableStateOf(setOf<Int>())
        private set

    init {
        observeFavoriteMovies()
    }

    private fun observeFavoriteMovies() {
        viewModelScope.launch {
            dao.getAllMovies().collect { movies ->
                _favoriteMovies.value = movies
                favoriteIds = movies.map { it.id }.toSet()
            }
        }
    }

    fun loadMovies(context: Context) {
        if (movieList.isNotEmpty()) return
        apiCall(context)
    }

    fun isFavorite(movieId: Int): Boolean {
        return favoriteIds.contains(movieId)
    }

    fun toggleFavorite(movie: MovieDataModel) {
        viewModelScope.launch {
            if (isFavorite(movie.id)) {
                dao.deleteById(movie.id)
            } else {
                dao.insert(
                    MovieEntity(
                        id = movie.id,
                        title = movie.title,
                        posterPath = movie.posterPath,
                        overview = movie.overview,
                        releaseDate = movie.releaseDate,
                        genreIds = movie.genreIds
                    )
                )
            }
        }
    }

    fun deleteFavorite(movieId: Int) {
        viewModelScope.launch {
            dao.deleteById(movieId)
        }
    }

    private fun apiCall(context: Context) {
        val apiKey = "9dbc4f311e85cd974cbff94a32ce5657"
        val url = "https://api.themoviedb.org/3/movie/popular?api_key=$apiKey"

        //val queue = Volley.newRequestQueue(context)
        val queue = UnsafeVolley.newRequestQueue(context)

        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val resultsArray = jsonObject.getJSONArray("results")

                    movieList.clear()

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
                } catch (e: Exception) {
                    Log.d("MyLog", "Parse error: ${e.message}")
                }
            },
            { error ->
                Log.d("MyLog", "Error: $error")
            }
        )

        queue.add(request)
    }
}