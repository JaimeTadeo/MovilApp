package com.example.app.features.movies.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.app.features.movies.domain.model.MovieModel
import com.example.app.features.movies.presentation.error.ErrorMessageProvider
import org.koin.androidx.compose.koinViewModel
import java.util.Locale
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen() {
    val viewModel: MoviesViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val context = LocalContext.current
    val errorMessageProvider = remember { ErrorMessageProvider() }
    var showFavorites by remember { mutableStateOf(false) }

    if (showFavorites) {
        FavoritesScreen(
            viewModel = viewModel,
            onBack = { showFavorites = false }
        )
        return
    }

    LaunchedEffect(Unit) {
        viewModel.fetchPopularMovies()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Películas Populares") },
            actions = {
                IconButton(onClick = { showFavorites = true }) {
                    Icon(Icons.Default.Favorite, contentDescription = "Favoritos")
                }
            }
        )

        when (val currentState = state) {
            is MoviesViewModel.MoviesStateUI.Init -> {}
            is MoviesViewModel.MoviesStateUI.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is MoviesViewModel.MoviesStateUI.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = errorMessageProvider.getMessage(currentState.failure, context),
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.fetchPopularMovies() }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            is MoviesViewModel.MoviesStateUI.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(currentState.movies) { movie ->
                        val isFavorite = favorites.any { it.id == movie.id }
                        MovieItem(
                            movie = movie,
                            isFavorite = isFavorite,
                            onToggleFavorite = {
                                if (isFavorite) viewModel.removeFavorite(movie)
                                else viewModel.addFavorite(movie)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: MovieModel,
    isFavorite: Boolean,
    onToggleFavorite: (MovieModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { onToggleFavorite(movie) },
                        modifier = Modifier.background(Color.White, shape = RoundedCornerShape(50))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                }
            }
        }
    }
}
