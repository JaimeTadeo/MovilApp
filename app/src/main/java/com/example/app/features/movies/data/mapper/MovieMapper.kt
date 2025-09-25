package com.example.app.features.movies.data.mapper

import com.example.app.features.movies.domain.model.MovieModel
import com.example.app.features.movies.data.database.entity.MovieEntity

fun MovieModel.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterUrl,
    backdropPath = backdropUrl,
    releaseDate = releaseDate
)

fun MovieEntity.toModel(): MovieModel = MovieModel(
    id = id,
    title = title,
    overview = overview ?: "",
    posterUrl = posterPath ?: "",
    backdropUrl = backdropPath ?: "",
    releaseDate = releaseDate ?: "",
    rating = 0.0, // No se almacena rating en local
    popularity = 0.0 // No se almacena popularity en local
)

