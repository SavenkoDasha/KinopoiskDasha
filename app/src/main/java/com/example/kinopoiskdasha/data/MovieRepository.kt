package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.data.db.mapper.toEntity
import com.example.kinopoiskdasha.domain.Frame
import com.example.kinopoiskdasha.domain.Result
import com.example.kinopoiskdasha.domain.Movie
import com.example.kinopoiskdasha.domain.MovieResponse
import com.example.kinopoiskdasha.domain.mapping.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface MovieRepository {
    suspend fun getMovieResponse(order: String, page: Int = 1): Result<MovieResponse, Throwable>
    suspend fun saveMovies(vararg movie: Movie)
    suspend fun deleteMovies()
    suspend fun fetchMovie(id: Int): Result<Movie, Throwable>
    suspend fun fetchAllMovies(): Flow<List<Movie>>
    suspend fun getFrames(id: Int): Result<List<Frame>, Throwable>
}

class MovieRepositoryImpl @Inject constructor(
    private val source: MovieDataSource,
    private val localSource: MovieLocalSource,
) : MovieRepository {
    override suspend fun getMovieResponse(
        order: String,
        page: Int,
    ): Result<MovieResponse, Throwable> {
        return try {
            Result.Success(source.getMovie(order = order, page = page).mapToDomain())
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

     override suspend fun saveMovies(vararg movie: Movie) {
        val mappedMovies = movie.map { it.toEntity() }.toTypedArray()
        localSource.insertMovies(*mappedMovies)
    }

     override suspend fun deleteMovies() = localSource.deleteMovies()

     override suspend fun fetchMovie(id: Int): Result<Movie, Throwable> {
        return try {
           Result.Success(localSource.fetchMovie(id).mapToDomain())
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

     override suspend fun fetchAllMovies(): Flow<List<Movie>> {
        return localSource.fetchAllMovies().map {
                it.map { movieEntity ->
                    movieEntity.mapToDomain()
                }
            }
        }

    override suspend fun getFrames(id: Int): Result<List<Frame>, Throwable> {
        return try {
            Result.Success(source.getFrames(id).mapToDomain())
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }
}
