package com.example.test_movie_app.ui.theme.fav_screen

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.test_movie_app.ui.theme.room.MovieEntity

@Composable
fun FavScreenBody(
    modifier: Modifier = Modifier,
    favoriteMovies: List<MovieEntity>,
    onRemoveClick: (MovieEntity) -> Unit
) {
    if (favoriteMovies.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "List is empty :(",
                fontSize = 20.sp
            )
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(favoriteMovies) { movie ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                            contentDescription = movie.title,
                            modifier = Modifier
                                .width(120.dp)
                                .aspectRatio(2f / 3f)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                text = movie.title,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = movie.releaseDate,
                                fontSize = 14.sp
                            )

                            Text(
                                text = movie.overview,
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                            Box(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(top = 30.dp)
                            ) {
                                IconButton(
                                    modifier = Modifier.align(Alignment.BottomEnd),
                                    onClick = { onRemoveClick(movie) }
                                ) {
                                    Icon(
                                        modifier = Modifier.size(40.dp),
                                        painter = painterResource(R.drawable.ic_delete),
                                        contentDescription = "",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}