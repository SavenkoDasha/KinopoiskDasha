package com.example.kinopoiskdasha.retrofit

import com.example.kinopoiskdasha.data.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET
    suspend fun getMovieList(@Query("page") page: Int): List<MovieDto>
}
