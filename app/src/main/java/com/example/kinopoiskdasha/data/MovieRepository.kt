package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.domain.MovieResponse
import com.example.kinopoiskdasha.domain.mapping.mapToDomain

interface MovieRepository {
    suspend fun getMovieResponse(page: Int = 1): MovieResponse
}

class MovieRepositoryImpl(private val source: MovieDataSource) : MovieRepository {
    override suspend fun getMovieResponse(page: Int) = source.getMovie(page = page).mapToDomain()
}
