package com.example.kinopoiskdasha.domain

sealed class Result<out S, out E: Throwable> {
    data class  Success<out S, out E: Throwable>(val value: S): Result<S, E>()
    data class Error<out S, out E: Throwable>(val error: E): Result<S, E>()
}
