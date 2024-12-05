package com.example.kinopoiskdasha.domain

data class MovieResponse(
    val total: Int,
    val totalPages: Int,
    val items: List<Movie>,
)
