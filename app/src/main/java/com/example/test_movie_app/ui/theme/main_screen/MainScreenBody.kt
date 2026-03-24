package com.example.test_movie_app.ui.theme.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.test_movie_app.R
import com.example.test_movie_app.ui.theme.data.MovieDataModel

@Composable
fun MainScreenBody(
    modifier: Modifier,
    navController: NavController,
    selectedMovieForDesc: MutableState<Int?>,
    viewModel: MainScreenVM
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadMovies(context)
    }

    LazyColumn(modifier = modifier) {
        items(viewModel.movieList) { movie ->
            val isFav = viewModel.isFavorite(movie.id)

            MovieCard(
                movie = movie,
                isFav = isFav,
                navController = navController,
                selectedMovieForDesc = selectedMovieForDesc,
                onFavoriteClick = {
                    viewModel.toggleFavorite(movie)
                }
            )
        }
    }
}

@Composable
fun MovieCard(
    movie: MovieDataModel,
    isFav: Boolean,
    navController: NavController,
    selectedMovieForDesc: MutableState<Int?>,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .clickable {
                selectedMovieForDesc.value = movie.id
                navController.navigate("desc_screen")
            },
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
                    contentDescription = movie.title,
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
                        fontSize = 16.sp,
                        color = Color.Black,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 5,
                        text = movie.overview
                    )
                }
            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 10.dp),
                onClick = onFavoriteClick
            ) {
                Icon(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(R.drawable.ic_favorite),
                    contentDescription = "Favorite",
                    tint = if (isFav) Color.Red else Color.Black
                )
            }
        }
    }
}