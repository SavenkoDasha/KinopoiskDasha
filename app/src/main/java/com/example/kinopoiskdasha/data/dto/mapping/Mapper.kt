package com.example.kinopoiskdasha.data.dto.mapping

import com.example.kinopoiskdasha.data.dto.CountryDto
import com.example.kinopoiskdasha.data.dto.GenreDto
import com.example.kinopoiskdasha.data.dto.MovieDto
import com.example.kinopoiskdasha.domain.Movie

fun mapToDto(movieList: List<Movie>) =
    movieList.map { model ->
        MovieDto(
            id = model.id,
            nameOriginal = model.nameOriginal,
            description = model.description,
            genres = model.genres.map { genre -> GenreDto(genre) },
            startYear = model.startYear,
            countries = model.countries.map { country -> CountryDto(country) },
            imageUrl = model.imageUrl,
            ratingKinopoisk = model.ratingKinopoisk
        )
    }
