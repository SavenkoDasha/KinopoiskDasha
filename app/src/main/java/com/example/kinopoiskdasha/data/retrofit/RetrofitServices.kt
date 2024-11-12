package com.example.kinopoiskdasha.data.retrofit

import com.example.kinopoiskdasha.data.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitServices {
    @GET("films")
    @Headers("X-API-KEY: de1db718-950e-449d-88a1-39a41062cee6")
    suspend fun getMovieList(@Query("page") page: Int): MovieResponseDto
}
