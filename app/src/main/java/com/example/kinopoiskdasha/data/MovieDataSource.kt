package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.data.dto.MovieDto
import com.example.kinopoiskdasha.data.retrofit.Common
import com.example.kinopoiskdasha.data.retrofit.RetrofitServices

interface MovieDataSource {
    suspend fun getMovie(page: Int): List<MovieDto>
}

class MovieDataSourceImpl(
    private val movieService: RetrofitServices = Common.retrofitService
) : MovieDataSource {
    override suspend fun getMovie(page: Int): List<MovieDto> {
        return movieService.getMovieList(page)
    }
}
