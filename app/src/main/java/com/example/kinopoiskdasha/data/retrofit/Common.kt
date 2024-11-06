package com.example.kinopoiskdasha.data.retrofit

object Common {
    private val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}
