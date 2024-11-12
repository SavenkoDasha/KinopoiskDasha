package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.data.dto.MovieResponseDto
import com.example.kinopoiskdasha.data.retrofit.RetrofitServices

interface MovieDataSource {
    suspend fun getMovie(page: Int): MovieResponseDto
}

class MovieDataSourceImpl(
    private val movieService: RetrofitServices
) : MovieDataSource {
    override suspend fun getMovie(page: Int): MovieResponseDto {
        return movieService.getMovieList(page)
    }
}
