package com.example.kinopoiskdasha.domain

data class Movie(
    val id: Int,
    val nameOriginal: String?,
    val description: String?,
    val countries: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val startYear: String?,
    val ratingKinopoisk: Double?,
    val imageUrl: String?,
    val coverUrl: String? = null,
)
