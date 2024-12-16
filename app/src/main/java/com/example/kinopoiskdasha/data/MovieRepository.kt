package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.data.db.mapper.toEntity
import com.example.kinopoiskdasha.domain.Movie
import com.example.kinopoiskdasha.domain.MovieResponse
import com.example.kinopoiskdasha.domain.mapping.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface MovieRepository {
    suspend fun getMovieResponse(order: String, page: Int = 1): MovieResponse
    suspend fun saveMovies(vararg movie: Movie)
    suspend fun deleteMovies()
    suspend fun fetchMovie(id: Int): Movie
    suspend fun fetchAllMovies(): Flow<List<Movie>>
}

class MovieRepositoryImpl @Inject constructor(
    private val source: MovieDataSource,
    private val localSource: MovieLocalSource,
) : MovieRepository {
    override suspend fun getMovieResponse(order: String, page: Int) =
        source.getMovie(order = order, page = page).mapToDomain()

    override suspend fun saveMovies(vararg movie: Movie) {
        val mappedMovies = movie.map { it.toEntity() }.toTypedArray()
        localSource.insertMovies(*mappedMovies)
    }

    override suspend fun deleteMovies() = localSource.deleteMovies()

    override suspend fun fetchMovie(id: Int) = localSource.fetchMovie(id).mapToDomain()


    override suspend fun fetchAllMovies(): Flow<List<Movie>> = localSource.fetchAllMovies().map {
        it.map { movieEntity ->
            movieEntity.mapToDomain()
        }
    }
}
