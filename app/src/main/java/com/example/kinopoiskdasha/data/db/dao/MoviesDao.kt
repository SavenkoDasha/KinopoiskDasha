package com.example.kinopoiskdasha.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinopoiskdasha.data.db.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(vararg movies: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun deleteMovies()

    @Query("SELECT * FROM movies")
    fun fetchAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun fetchMovie(id: Int): MovieEntity
}
