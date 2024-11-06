package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.data.dto.MovieDto
import com.example.kinopoiskdasha.domain.Movie

interface MovieRepository {
    suspend fun getMovies(page: Int = 1): List<MovieDto>
}

class MovieRepositoryImpl(private val source: MovieDataSource) : MovieRepository {
    override suspend fun getMovies(page: Int): List<MovieDto> {
        return source.getMovie(page)
    }
}

class MovieMapper {
    fun mapToDto(movieList: List<Movie>): List<MovieDto> {
        return movieList.map { model ->
            MovieDto(
                nameOriginal = model.nameOriginal,
                description = model.description,
                genres = model.genres,
                endYear = model.endYear,
                startYear = model.startYear,
                countries = model.countries,
                imageUrl = model.imageUrl,
                ratingKinopoisk = model.ratingKinopoisk
            )
        }
    }

    fun mapToModel(movieList: List<MovieDto>): List<Movie> {
        return movieList.map { dto ->
            Movie(
                nameOriginal = dto.nameOriginal,
                description = dto.description,
                genres = dto.genres,
                endYear = dto.endYear,
                startYear = dto.startYear,
                countries = dto.countries,
                imageUrl = dto.imageUrl,
                ratingKinopoisk = dto.ratingKinopoisk
            )
        }
    }
}
