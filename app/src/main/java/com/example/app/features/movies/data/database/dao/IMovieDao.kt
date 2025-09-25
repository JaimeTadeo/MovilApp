package com.example.app.features.movies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app.features.movies.data.database.entity.MovieEntity

@Dao
interface IMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}

