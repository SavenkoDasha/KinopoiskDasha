package com.example.kinopoiskdasha.ui.model

data class MovieUi(
    val id: Int,
    val nameOriginal: String,
    val description: String,
    val genres: String,
    val movieYearAndCountry: String,
    val ratingKinopoisk: String,
    val imageUrl: String,
): ListItem
