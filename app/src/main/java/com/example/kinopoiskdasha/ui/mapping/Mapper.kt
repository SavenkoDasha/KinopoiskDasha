package com.example.kinopoiskdasha.ui.mapping

import com.example.kinopoiskdasha.domain.Frame
import com.example.kinopoiskdasha.domain.Movie
import com.example.kinopoiskdasha.ui.model.ImageItem
import com.example.kinopoiskdasha.ui.model.MovieDetailUi
import com.example.kinopoiskdasha.ui.model.MovieUi

fun Frame.mapToImageItem() = ImageItem(
    imageUrl = imageUrl ?: previewUrl,
)

fun Movie.mapToUI() = MovieUi(
    id = id,
    nameOriginal = nameOriginal ?: "empty",
    description = description ?: "empty",
    movieYearAndCountry = "$startYear, ${countries.joinToString()}",
    genres = genres.joinToString(),
    ratingKinopoisk = ratingKinopoisk.toString(),
    imageUrl = imageUrl ?: "empty",
)

fun Movie.mapToDetailUi() = MovieDetailUi(
    id = id,
    nameOriginal = nameOriginal ?: "empty",
    description = genres.joinToString(),
    movieYearAndCountry = "$startYear, ${countries.joinToString()}",
    genres = genres.joinToString(),
    ratingKinopoisk = ratingKinopoisk.toString(),
    coverUrl = coverUrl ?: imageUrl ?: "empty",
)
