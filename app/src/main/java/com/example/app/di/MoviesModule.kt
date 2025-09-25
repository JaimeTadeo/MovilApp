package com.example.app.di

import com.example.app.features.movies.data.api.MoviesApiService
import com.example.app.features.movies.data.datasource.MoviesRemoteDataSource
import com.example.app.features.movies.data.repository.MoviesRepository
import com.example.app.features.movies.domain.repository.IMoviesRepository
import com.example.app.features.movies.domain.usecase.GetPopularMoviesUseCase
import com.example.app.features.movies.presentation.MoviesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val moviesModule = module {
    // API Service
    single<MoviesApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApiService::class.java)
    }

    // Data Source
    single { MoviesRemoteDataSource(get()) }

    // Repository
    single<IMoviesRepository> { MoviesRepository(get()) }

    // Use Case
    single { GetPopularMoviesUseCase(get()) }

    // ViewModel
    viewModel { MoviesViewModel(get(), get()) }
}
