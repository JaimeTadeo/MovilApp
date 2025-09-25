package com.example.app.features.movies.data.api

import com.example.app.features.movies.data.model.MovieDto
import com.example.app.features.movies.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.*

interface MoviesApiService {
    @GET("discover/movie")
    suspend fun getPopularMovies(
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    @PUT("movie/{movie_id}")
    suspend fun updateMovie(
        @Path("movie_id") movieId: Int,
        @Body movie: MovieDto,
        @Header("Authorization") authHeader: String
    ): Response<MovieDto>

    @DELETE("movie/{movie_id}")
    suspend fun deleteMovie(
        @Path("movie_id") movieId: Int,
        @Header("Authorization") authHeader: String
    ): Response<Unit>
}

