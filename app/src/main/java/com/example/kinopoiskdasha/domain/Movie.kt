package com.example.kinopoiskdasha.domain

data class Movie(
    val nameOriginal: String?,
    val description: String?,
    val countries: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val startYear: String?,
    val endYear: String?,
    val ratingKinopoisk: Double?,
    val imageUrl: String?,
)
