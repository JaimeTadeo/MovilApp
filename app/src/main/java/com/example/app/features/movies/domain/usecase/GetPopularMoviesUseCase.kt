package com.example.app.features.movies.domain.usecase

import com.example.app.features.movies.domain.model.MovieModel
import com.example.app.features.movies.domain.repository.IMoviesRepository

class GetPopularMoviesUseCase(
    private val repository: IMoviesRepository
) {
    suspend fun invoke(): Result<List<MovieModel>> {
        return repository.getPopularMovies()
    }
}
