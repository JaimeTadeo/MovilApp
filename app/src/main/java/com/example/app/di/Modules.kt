package com.example.app.di

import com.example.app.data.api.IGitHubApiService
import com.example.app.data.api.IStockApiService
import com.example.app.data.datasource.GitHubRepositoryImpl
import com.example.app.data.datasource.StockRepositoryImpl
import com.example.app.domain.repository.IGitHubRepository
import com.example.app.domain.repository.IStockRepository
import com.example.app.domain.usecases.FindByNickname
import com.example.app.features.movies.data.api.MoviesApiService
import com.example.app.features.movies.data.datasource.MoviesRemoteDataSource
import com.example.app.features.movies.data.repository.MoviesRepository
import com.example.app.features.movies.domain.repository.IMoviesRepository
import com.example.app.features.movies.domain.usecase.GetPopularMoviesUseCase
import com.example.app.features.movies.presentation.MoviesViewModel
import com.example.app.features.dollar.data.database.AppRoomDatabase
import com.example.app.features.dollar.data.database.dao.IDollarDao
import com.example.app.features.dollar.data.datasource.DollarLocalDataSource
import com.example.app.feature.profile.github.application.GithubViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.app.features.movies.data.database.dao.IMovieDao

val appModule = module {
    single<Retrofit>(qualifier = org.koin.core.qualifier.named("github")) {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Retrofit>(qualifier = org.koin.core.qualifier.named("stock")) {
        Retrofit.Builder()
            .baseUrl("https://www.alphavantage.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Retrofit>(qualifier = org.koin.core.qualifier.named("movies")) {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<IGitHubApiService> {
        get<Retrofit>(qualifier = org.koin.core.qualifier.named("github")).create(IGitHubApiService::class.java)
    }

    single<IStockApiService> {
        get<Retrofit>(qualifier = org.koin.core.qualifier.named("stock")).create(IStockApiService::class.java)
    }

    single<MoviesApiService> {
        get<Retrofit>(qualifier = org.koin.core.qualifier.named("movies")).create(MoviesApiService::class.java)
    }

    single<IGitHubRepository> { GitHubRepositoryImpl(get<IGitHubApiService>()) }
    single<IStockRepository> { StockRepositoryImpl(get<IStockApiService>()) }
    single<MoviesRemoteDataSource> { MoviesRemoteDataSource(get()) }
    single<IMovieDao> { get<AppRoomDatabase>().movieDao() }
    single<DollarLocalDataSource> { DollarLocalDataSource(get()) }
    single<IMoviesRepository> { MoviesRepository(get(), get()) }
    single<MoviesRepository> { MoviesRepository(get(), get()) }

    factory { FindByNickname(get<IGitHubRepository>()) }
    factory { GetPopularMoviesUseCase(get<IMoviesRepository>()) }

    viewModel { GithubViewModel(get<FindByNickname>()) }
    viewModel { MoviesViewModel(get(), get()) }

    // --- Dollar Feature DI ---
    single { AppRoomDatabase.getDatabase(androidContext()) }
    single<IDollarDao> { get<AppRoomDatabase>().dollarDao() }
    single<com.example.app.features.dollar.datasource.RealTimeRemoteDataSource> { com.example.app.features.dollar.datasource.RealTimeRemoteDataSource() }
    single<com.example.app.domain.repository.IDollarRepository> { com.example.app.features.dollar.data.DollarRepository(get(), get()) }
    factory { com.example.app.domain.usecases.FetchDollarUseCase(get()) }
    viewModel { com.example.app.features.dollar.DollarViewModel(get()) }
}