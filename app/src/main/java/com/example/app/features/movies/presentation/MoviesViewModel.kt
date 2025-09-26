package com.example.app.features.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.features.movies.data.model.MovieDto
import com.example.app.features.movies.data.repository.MoviesRepository
import com.example.app.features.movies.domain.error.Failure
import com.example.app.features.movies.domain.model.MovieModel
import com.example.app.features.movies.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val usecase: GetPopularMoviesUseCase,
    private val repository: MoviesRepository
): ViewModel() {

    sealed class MoviesStateUI {
        object Init: MoviesStateUI()
        object Loading: MoviesStateUI()
        data class Error(val failure: Failure): MoviesStateUI()
        data class Success(val movies: List<MovieModel>): MoviesStateUI()
    }

    private val _state = MutableStateFlow<MoviesStateUI>(MoviesStateUI.Init)
    val state: StateFlow<MoviesStateUI> = _state.asStateFlow()

    private val _favorites = MutableStateFlow<List<MovieModel>>(emptyList())
    val favorites: StateFlow<List<MovieModel>> = _favorites.asStateFlow()

    fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = MoviesStateUI.Loading
            val result = usecase.invoke()

            result.fold(
                onSuccess = { movies ->
                    try {
                        repository.saveMoviesLocally(movies) // Guardar localmente
                        _state.value = MoviesStateUI.Success(movies)
                    } catch (e: Exception) {
                        _state.value = MoviesStateUI.Error(Failure.Unknown(Exception("Error al guardar en la base de datos local: ${e.localizedMessage}")))
                    }
                },
                onFailure = {
                    _state.value = MoviesStateUI.Error(failure = it as Failure)
                }
            )
        }
    }

    fun deleteMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = MoviesStateUI.Loading
            val result = repository.deleteMovie(movieId)
            if (result.isSuccess) {
                fetchPopularMovies()
            } else {
                _state.value = MoviesStateUI.Error(Failure.Unknown(Exception("Error al eliminar película")))
            }
        }
    }

    fun updateMovie(movie: MovieModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = MoviesStateUI.Loading
            val dto = MovieDto(
                id = movie.id,
                title = movie.title,
                overview = movie.overview,
                posterPath = movie.posterUrl.removePrefix("https://image.tmdb.org/t/p/w185"),
                backdropPath = movie.backdropUrl.removePrefix("https://image.tmdb.org/t/p/w500"),
                releaseDate = movie.releaseDate,
                voteAverage = movie.rating,
                popularity = movie.popularity
            )
            val result = repository.updateMovie(movie.id, dto)
            if (result.isSuccess) {
                fetchPopularMovies()
            } else {
                _state.value = MoviesStateUI.Error(Failure.Unknown(Exception("Error al actualizar película")))
            }
        }
    }

    fun addFavorite(movie: MovieModel) {
        _favorites.value = _favorites.value.toMutableList().apply {
            if (none { it.id == movie.id }) add(movie)
        }
    }

    fun removeFavorite(movie: MovieModel) {
        _favorites.value = _favorites.value.filter { it.id != movie.id }
    }

    fun updateFavorite(movie: MovieModel) {
        _favorites.value = _favorites.value.map {
            if (it.id == movie.id) movie else it
        }
    }
}
