package com.example.kinopoiskdasha.data.db.mapper

import com.example.kinopoiskdasha.data.db.MovieEntity
import com.example.kinopoiskdasha.domain.Movie

fun Movie.toEntity() = MovieEntity(
        id = id,
        name = nameOriginal.orEmpty(),
        description = description.orEmpty(),
        countries = countries,
        genres = genres,
        startYear = startYear.orEmpty(),
        ratingKinopoisk = ratingKinopoisk ?: 0.0,
        imageUrl = imageUrl.orEmpty(),
    )
