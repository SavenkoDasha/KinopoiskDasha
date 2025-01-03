package com.example.kinopoiskdasha.data.retrofit

import com.example.kinopoiskdasha.data.dto.FramesResponseDto
import com.example.kinopoiskdasha.data.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {
    @GET("films")
    @Headers("X-API-KEY: 601187a1-7cd5-4ece-8000-bd7bd6d03927")
    suspend fun getMovieList(@Query("order") year: String, @Query("page") page: Int, @Query("yearTo") yearTo: Int = 2023 ): MovieResponseDto

    @GET("films/{id}/images")
    @Headers("X-API-KEY: 601187a1-7cd5-4ece-8000-bd7bd6d03927")
    suspend fun getMovieFrames(@Path("id") id: Int, @Query("type") type: String = "STILL"): FramesResponseDto
}
