package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.data.dto.FramesResponseDto
import com.example.kinopoiskdasha.data.dto.MovieResponseDto
import com.example.kinopoiskdasha.data.retrofit.RetrofitServices
import javax.inject.Inject

interface MovieDataSource {
    suspend fun getMovie(order: String, page: Int): MovieResponseDto
    suspend fun getFrames(id: Int): FramesResponseDto
}

class MovieDataSourceImpl @Inject constructor (
    private val movieService: RetrofitServices,
) : MovieDataSource {
    override suspend fun getMovie(order: String, page: Int): MovieResponseDto {
        return movieService.getMovieList(order, page)
    }

    override suspend fun getFrames(id: Int): FramesResponseDto {
        return movieService.getMovieFrames(id)
    }
}
