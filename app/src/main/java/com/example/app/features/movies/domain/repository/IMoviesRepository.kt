package com.example.app.features.movies.domain.repository

import com.example.app.features.movies.domain.model.MovieModel

interface IMoviesRepository {
    suspend fun getPopularMovies(): Result<List<MovieModel>>
}
