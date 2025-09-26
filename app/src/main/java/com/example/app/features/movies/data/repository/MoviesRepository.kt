package com.example.app.features.movies.data.repository

import com.example.app.features.movies.data.datasource.MoviesRemoteDataSource
import com.example.app.features.movies.data.error.DataException
import com.example.app.features.movies.domain.error.Failure
import com.example.app.features.movies.domain.model.MovieModel
import com.example.app.features.movies.domain.repository.IMoviesRepository
import com.example.app.features.movies.data.local.MovieDao
import com.example.app.features.movies.data.local.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val movieDao: MovieDao // Nuevo parámetro para acceso local
) : IMoviesRepository {

    override suspend fun getPopularMovies(): Result<List<MovieModel>> {
        val response = remoteDataSource.getPopularMovies()

        response.fold(
            onSuccess = { movieResponse ->
                val movies = movieResponse.results.map { dto ->
                    MovieModel(
                        id = dto.id,
                        title = dto.title,
                        overview = dto.overview,
                        posterUrl = "https://image.tmdb.org/t/p/w185${dto.posterPath}",
                        backdropUrl = "https://image.tmdb.org/t/p/w500${dto.backdropPath}",
                        releaseDate = dto.releaseDate,
                        rating = dto.voteAverage,
                        popularity = dto.popularity
                    )
                }
                return Result.success(movies)
            },
            onFailure = { exception ->
                val failure = when (exception) {
                    is DataException.Network -> Failure.NetworkConnection
                    is DataException.HttpNotFound -> Failure.NotFound
                    is DataException.NoContent -> Failure.EmptyBody
                    is DataException.Unknown -> Failure.Unknown(exception)
                    else -> Failure.Unknown(exception)
                }
                return Result.failure(failure)
            }
        )
    }

    suspend fun updateMovie(movieId: Int, movieDto: com.example.app.features.movies.data.model.MovieDto): Result<com.example.app.features.movies.data.model.MovieDto> {
        return remoteDataSource.updateMovie(movieId, movieDto)
    }

    suspend fun deleteMovie(movieId: Int): Result<Unit> {
        return remoteDataSource.deleteMovie(movieId)
    }

    suspend fun saveMoviesLocally(movies: List<MovieModel>) {
        withContext(Dispatchers.IO) {
            val entities = movies.map {
                MovieEntity(
                    id = it.id,
                    title = it.title,
                    overview = it.overview,
                    posterUrl = it.posterUrl,
                    backdropUrl = it.backdropUrl,
                    releaseDate = it.releaseDate,
                    rating = it.rating,
                    popularity = it.popularity
                )
            }
            movieDao.deleteAllMovies()
            movieDao.insertMovies(entities)
        }
    }

    suspend fun getLocalMovies(): List<MovieModel> = withContext(Dispatchers.IO) {
        movieDao.getAllMovies().map {
            MovieModel(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterUrl = it.posterUrl,
                backdropUrl = it.backdropUrl,
                releaseDate = it.releaseDate,
                rating = it.rating,
                popularity = it.popularity
            )
        }
    }
}
