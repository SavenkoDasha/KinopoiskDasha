package com.example.kinopoiskdasha.ui.mapping

import com.example.kinopoiskdasha.domain.Movie
import com.example.kinopoiskdasha.ui.model.MovieUi

fun Movie.mapToUI() = MovieUi(
    id = id,
    nameOriginal = nameOriginal ?: "empty",
    description = description ?: "empty",
    filmYearAndCountry = startYear + ", " + countries.joinToString(),
    genres = genres.joinToString(),
    ratingKinopoisk = ratingKinopoisk.toString(),
    imageUrl = imageUrl ?: "empty",
)