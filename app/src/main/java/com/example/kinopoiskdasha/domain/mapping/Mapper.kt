package com.example.kinopoiskdasha.domain.mapping

import com.example.kinopoiskdasha.data.db.MovieEntity
import com.example.kinopoiskdasha.data.dto.FramesResponseDto
import com.example.kinopoiskdasha.data.dto.MovieDto
import com.example.kinopoiskdasha.domain.Movie
import com.example.kinopoiskdasha.data.dto.MovieResponseDto
import com.example.kinopoiskdasha.domain.Frame
import com.example.kinopoiskdasha.domain.MovieResponse


fun FramesResponseDto.mapToDomain() = this.items.map {
    Frame(
        imageUrl = it.imageUrl,
        previewUrl = it.previewUrl,
    )
}

fun MovieDto.mapToDomain() = Movie(
    id = id,
    nameOriginal = nameRussian ?: nameEnglish ?: nameOriginal,
    description = description,
    genres = genres.map { genreDto ->
        genreDto.genre
    },
    startYear = startYear,
    countries = countries.map { countryDto ->
        countryDto.country
    },
    imageUrl = imageUrl,
    coverUrl = coverUrl,
    ratingKinopoisk = ratingKinopoisk
)

fun MovieResponseDto.mapToDomain() = MovieResponse(
    total = total,
    totalPages = totalPages,
    items = items.map { it.mapToDomain() }
)


fun MovieEntity.mapToDomain() = Movie(
        id = id,
        nameOriginal = name,
        description = description,
        countries = countries,
        genres = genres,
        startYear = startYear,
        ratingKinopoisk = ratingKinopoisk,
        imageUrl = imageUrl,
    )
