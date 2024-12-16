package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.data.db.MovieEntity
import com.example.kinopoiskdasha.data.db.dao.MoviesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieLocalSource {
    suspend fun insertMovies(vararg movies: MovieEntity)

    suspend fun deleteMovies()

    suspend fun fetchAllMovies(): Flow<List<MovieEntity>>

    suspend fun fetchMovie(id: Int): MovieEntity
}

class MovieLocalSourceImpl @Inject constructor(
    private val dao: MoviesDao
) : MovieLocalSource {
    override suspend fun insertMovies(vararg movies: MovieEntity) {
        dao.insertMovies(*movies)
    }

    override suspend fun deleteMovies() = dao.deleteMovies()

    override suspend fun fetchMovie(id: Int): MovieEntity = dao.fetchMovie(id)


    override suspend fun fetchAllMovies(): Flow<List<MovieEntity>> = dao.fetchAllMovies()

}
