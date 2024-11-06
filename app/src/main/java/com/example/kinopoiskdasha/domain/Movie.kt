package com.example.kinopoiskdasha.domain

data class Movie(
    val nameOriginal: String?,
    val description: String?,
    val countries: String?,
    val genres: String?,
    val startYear: String?,
    val endYear: String?,
    val ratingKinopoisk: Double?,
    val imageUrl: String?,
)
