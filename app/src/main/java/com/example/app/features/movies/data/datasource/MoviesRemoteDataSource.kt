package com.example.app.features.movies.data.datasource

import android.util.Base64
import com.example.app.features.movies.data.api.MoviesApiService
import com.example.app.features.movies.data.error.DataException
import com.example.app.features.movies.data.model.MovieDto
import com.example.app.features.movies.data.model.MovieResponse
import java.io.IOException

class MoviesRemoteDataSource(
    private val apiService: MoviesApiService
) {
    suspend fun getPopularMovies(): Result<MovieResponse> {
        return try {
            val response = apiService.getPopularMovies(
                apiKey = "fa3e844ce31744388e07fa47c7c5d8c3"
            )

            when {
                response.isSuccessful -> {
                    val body = response.body()
                    if (body != null && body.results.isNotEmpty()) {
                        Result.success(body)
                    } else {
                        Result.failure(DataException.NoContent)
                    }
                }
                response.code() == 404 -> Result.failure(DataException.HttpNotFound)
                else -> Result.failure(DataException.Unknown("Error HTTP: ${response.code()}"))
            }
        } catch (e: IOException) {
            Result.failure(DataException.Network)
        } catch (e: Exception) {
            Result.failure(DataException.Unknown(e.message ?: "Error desconocido"))
        }
    }

    private fun getAuthHeader(): String {
        val credentials = "wasa11121314:Monoarana1"
        val encoded = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        return "Basic $encoded"
    }

    suspend fun updateMovie(movieId: Int, movie: MovieDto): Result<MovieDto> {
        return try {
            val response = apiService.updateMovie(
                movieId = movieId,
                movie = movie,
                authHeader = getAuthHeader()
            )
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(DataException.NoContent)
            } else {
                Result.failure(DataException.Unknown("Error HTTP: ${response.code()}"))
            }
        } catch (e: IOException) {
            Result.failure(DataException.Network)
        } catch (e: Exception) {
            Result.failure(DataException.Unknown(e.message ?: "Error desconocido"))
        }
    }

    suspend fun deleteMovie(movieId: Int): Result<Unit> {
        return try {
            val response = apiService.deleteMovie(
                movieId = movieId,
                authHeader = getAuthHeader()
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(DataException.Unknown("Error HTTP: ${response.code()}"))
            }
        } catch (e: IOException) {
            Result.failure(DataException.Network)
        } catch (e: Exception) {
            Result.failure(DataException.Unknown(e.message ?: "Error desconocido"))
        }
    }
}
