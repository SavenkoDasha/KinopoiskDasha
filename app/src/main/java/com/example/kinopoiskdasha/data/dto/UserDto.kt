package com.example.kinopoiskdasha.data.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserData(
    val email: String,
    val password: String,
)
