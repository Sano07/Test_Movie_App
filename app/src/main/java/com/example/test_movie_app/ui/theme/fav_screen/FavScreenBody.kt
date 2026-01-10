package com.example.test_movie_app.ui.theme.fav_screen

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.test_movie_app.R
import com.example.test_movie_app.ui.theme.data.MovieDataModel
import com.example.test_movie_app.ui.theme.main_screen.MainScreenVM

@Composable
fun FavScreenBody(
    //modifier: Modifier,
    //favMovie: Set<Int>,
    onFavMovieChange: (Int) -> Unit,
    //navController: NavController,
    favoriteMovies: List<MovieDataModel>,
    viewModel: MainScreenVM = viewModel()
) {

    LazyColumn {
        itemsIndexed(favoriteMovies) { _, movie ->
            //val isFav = favMovie.contains(movie.id)
            MovieCard(
               // favMovie,
                onFavMovieChange,
                movie,
                //isFav,
                //navController,
            )
        }
    }
}

@Composable
fun MovieCard(
    //favMovie: Set<Int>,
    onFavMovieChange: (Int) -> Unit,
    movie: MovieDataModel,
    //isFav: Boolean,
    //navController: NavController,
) {

    var isPressedDelete by remember { mutableStateOf(false) }
    val scaleDelete by animateFloatAsState(
        if (isPressedDelete) 0.9f else 1f,
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .clickable {},
        shape = RoundedCornerShape(15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                    modifier = Modifier
                        .weight(0.4f)
                        .aspectRatio(2f / 3f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        text = movie.title
                    )
                    Text(
                        modifier = Modifier.padding(5.dp),
                        fontSize = 15.sp,
                        color = Color.Black,
                        text = movie.releaseDate
                    )
                    Text(
                        modifier = Modifier.padding(5.dp),
                        fontSize = 20.sp,
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 5,
                        text = movie.overview
                    )
                }
            }
            Box(
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scaleDelete,
                        scaleY = scaleDelete
                    )
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> isPressedDelete = true
                            MotionEvent.ACTION_UP -> {
                                isPressedDelete = false
                            }

                            MotionEvent.ACTION_CANCEL -> isPressedDelete = false
                        }
                        false
                    }
            ) {
                Button(
                    onClick = {
                        onFavMovieChange(movie.id)
                        isPressedDelete = false
                    },
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .width(120.dp),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    border = BorderStroke(2.dp, Color.Black)
                ) {
                    Text(text = "Delete", color = Color.Black)
                }
            }
        }
    }
}