package com.example.kinopoiskdasha.ui.model

data class MovieUi(
    val id: Int,
    val nameOriginal: String?,
    val description: String?,
    val genres: List<String> = emptyList(),
    val filmYearAndCountry: String?,
    val ratingKinopoisk: Double?,
    val imageUrl: String?,
)