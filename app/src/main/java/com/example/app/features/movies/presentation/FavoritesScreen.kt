package com.example.app.features.movies.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.app.features.movies.domain.model.MovieModel
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: MoviesViewModel,
    onBack: () -> Unit
) {
    val favorites by viewModel.favorites.collectAsState()
    var editingMovie by remember { mutableStateOf<MovieModel?>(null) }
    var editTitle by remember { mutableStateOf(TextFieldValue("")) }
    var editOverview by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Favoritos") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        )
        if (favorites.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No hay favoritos.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favorites, key = { it.id }) { movie ->
                    var offsetX by remember { mutableStateOf(0f) }
                    val animatable = remember { Animatable(0f) }
                    var animateBack by remember { mutableStateOf(false) }
                    val threshold = 150f
                    LaunchedEffect(animateBack) {
                        if (animateBack) {
                            animatable.snapTo(offsetX)
                            animatable.animateTo(0f, animationSpec = tween(300)) {
                                offsetX = value
                            }
                            animateBack = false
                        }
                    }
                    Box(
                        modifier = Modifier
                            .offset { IntOffset(offsetX.roundToInt(), 0) }
                            .pointerInput(movie.id) {
                                detectHorizontalDragGestures(
                                    onDragStart = {},
                                    onHorizontalDrag = { _, dragAmount ->
                                        offsetX = (offsetX + dragAmount).coerceAtMost(0f)
                                    },
                                    onDragEnd = {
                                        if (offsetX < -threshold) {
                                            viewModel.removeFavorite(movie)
                                        } else {
                                            animateBack = true
                                        }
                                    },
                                    onDragCancel = {
                                        animateBack = true
                                    }
                                )
                            }
                    ) {
                        FavoriteItem(
                            movie = movie,
                            onEdit = { selectedMovie ->
                                editingMovie = selectedMovie
                                editTitle = TextFieldValue(selectedMovie.title)
                                editOverview = TextFieldValue(selectedMovie.overview)
                            },
                            onDelete = { selectedMovie -> viewModel.removeFavorite(selectedMovie) }
                        )
                    }
                }
            }
        }
    }

    if (editingMovie != null) {
        AlertDialog(
            onDismissRequest = { editingMovie = null },
            title = { Text("Editar Favorito") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editTitle,
                        onValueChange = { editTitle = it },
                        label = { Text("Título") }
                    )
                    OutlinedTextField(
                        value = editOverview,
                        onValueChange = { editOverview = it },
                        label = { Text("Descripción") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    editingMovie?.let {
                        viewModel.updateFavorite(
                            it.copy(title = editTitle.text, overview = editOverview.text)
                        )
                    }
                    editingMovie = null
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { editingMovie = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun FavoriteItem(
    movie: MovieModel,
    onEdit: (MovieModel) -> Unit,
    onDelete: (MovieModel) -> Unit
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
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.background(Color.White, shape = RoundedCornerShape(50))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorito",
                            tint = Color.Red
                        )
                    }
                    IconButton(onClick = { onEdit(movie) }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { onDelete(movie) }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Eliminar de favoritos")
                    }
                }
            }
        }
    }
}
