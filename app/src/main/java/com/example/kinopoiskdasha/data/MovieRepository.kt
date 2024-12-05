package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.domain.MovieResponse
import com.example.kinopoiskdasha.domain.mapping.mapToDomain
import javax.inject.Inject

interface MovieRepository {
    suspend fun getMovieResponse(order: String, page: Int = 1): MovieResponse
}

class MovieRepositoryImpl @Inject constructor(
    private val source: MovieDataSource
) : MovieRepository {
    override suspend fun getMovieResponse(order: String, page: Int) = source.getMovie(order = order, page = page).mapToDomain()
}
