package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.data.dto.CountryDto
import com.example.kinopoiskdasha.data.dto.GenreDto
import com.example.kinopoiskdasha.data.dto.MovieDto
import com.example.kinopoiskdasha.data.dto.MovieResponseDto
import com.example.kinopoiskdasha.domain.Movie
import com.example.kinopoiskdasha.domain.MovieResponse

interface MovieRepository {
    suspend fun getMovieResponse(page: Int = 1): MovieResponse
}

class MovieRepositoryImpl(private val source: MovieDataSource) : MovieRepository {
    override suspend fun getMovieResponse(page: Int): MovieResponse {
        val movieResponseDto = source.getMovie(page)
        val mapper = MovieMapper()
        return mapper.mapResponseDtoToModel(movieResponseDto)
    }
}

class MovieMapper {
    fun mapToDto(movieList: List<Movie>): List<MovieDto> {
        return movieList.map { model ->
            MovieDto(
                nameOriginal = model.nameOriginal,
                description = model.description,
                genres = model.genres.map { genre -> GenreDto(genre) },
                endYear = model.endYear,
                startYear = model.startYear,
                countries = model.countries.map { country -> CountryDto(country) },
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
                genres = dto.genres.map { genreDto ->
                    genreDto.genre
                },
                endYear = dto.endYear,
                startYear = dto.startYear,
                countries = dto.countries.map { countryDto ->
                    countryDto.country
                },
                imageUrl = dto.imageUrl,
                ratingKinopoisk = dto.ratingKinopoisk
            )
        }
    }

    fun mapResponseDtoToModel(movieResponse: MovieResponseDto) = MovieResponse(
        total = movieResponse.total,
        totalPages = movieResponse.totalPages,
        items = mapToModel(movieResponse.items)
    )

    fun mapResponseModelToDto(movieResponse: MovieResponse) = MovieResponseDto(
        total = movieResponse.total,
        totalPages = movieResponse.totalPages,
        items = mapToDto(movieResponse.items)
    )

}
