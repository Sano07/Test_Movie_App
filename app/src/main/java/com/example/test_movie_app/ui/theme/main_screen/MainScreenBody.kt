package com.example.test_movie_app.ui.theme.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
    favMovie: Set<Int>,
    onFavMovieUpdate: (Set<Int>) -> Unit,
    onFavMovieChange: (Int) -> Unit,
    navController: NavController,
    selectedMovieForDesc: MutableState<Int?>,
    viewModel: MainScreenVM = viewModel(),
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadMovies(context)
    }


    LazyColumn {
        itemsIndexed(viewModel.movieList) { _, movie ->
            val isFav = favMovie.contains(movie.id)
            MovieCard(modifier, favMovie, onFavMovieChange, movie, isFav, navController, selectedMovieForDesc)
        }
    }
}

@Composable
fun MovieCard(
    modifier: Modifier,
    favMovie: Set<Int>,
    onFavMovieChange: (Int) -> Unit, movie: MovieDataModel,
    isFav: Boolean,
    navController: NavController,
    selectedMovieForDesc: MutableState<Int?>,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .clickable {
                navController.navigate("desc_screen")
                selectedMovieForDesc.value = movie.id
            },
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            AsyncImage(
                model = { "https://api.themoviedb.org/${movie.posterPath}" },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .aspectRatio(16 / 11f)
                    .clip(RoundedCornerShape(8.dp)),
                contentDescription = "машинка для примера",
                contentScale = ContentScale.Crop,
            )
            Column {
                Text(
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    text = movie.title
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                ) {
                    Text(
                        fontSize = 15.sp,
                        color = Color.Black,
                        text = movie.releaseDate
                    )
                }
                Text(
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    text = movie.overview
                )
                IconButton(
                    onClick = {
                        onFavMovieChange(movie.id)
                    },
                    enabled = !favMovie.contains(movie.id),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 10.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(60.dp),
                        painter = painterResource(R.drawable.ic_favorite),
                        contentDescription = "Добавлено в избранное",
                        tint = if (isFav) Color.Red else Color.Black
                    )
                }
            }
        }
    }
}