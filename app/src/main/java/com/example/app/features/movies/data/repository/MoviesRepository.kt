package com.example.app.features.movies.data.repository

import com.example.app.features.movies.data.datasource.MoviesRemoteDataSource
import com.example.app.features.movies.data.error.DataException
import com.example.app.features.movies.domain.error.Failure
import com.example.app.features.movies.domain.model.MovieModel
import com.example.app.features.movies.domain.repository.IMoviesRepository

class MoviesRepository(
    private val remoteDataSource: MoviesRemoteDataSource
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
}

