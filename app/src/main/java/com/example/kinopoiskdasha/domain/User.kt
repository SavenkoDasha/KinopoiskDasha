package com.example.kinopoiskdasha.domain

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.datetime.LocalDateTime

data class User(
    val email: String,
    val password: String,
    val lastSuccessfulLogin: LocalDateTime,
)

class LocalDateTimeAdapter {
    @ToJson
    fun toJson(value: LocalDateTime): String {
        return value.toString()
    }

    @FromJson
    fun fromJson(value: String): LocalDateTime {
        return LocalDateTime.parse(value)
    }
}
