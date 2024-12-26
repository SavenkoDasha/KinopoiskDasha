package com.example.kinopoiskdasha.ui.model

data class MovieDetailUi(
    val id: Int,
    val nameOriginal: String,
    val description: String,
    val genres: String,
    val movieYearAndCountry: String,
    val ratingKinopoisk: String,
    val coverUrl: String,
)
