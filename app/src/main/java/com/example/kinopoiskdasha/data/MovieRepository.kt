package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.data.dto.CountryDto
import com.example.kinopoiskdasha.data.dto.GenreDto
import com.example.kinopoiskdasha.data.dto.MovieDto
import com.example.kinopoiskdasha.data.dto.MovieResponseDto
import com.example.kinopoiskdasha.domain.Movie
import com.example.kinopoiskdasha.domain.MovieResponse
import com.example.kinopoiskdasha.ui.model.MovieUi

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
    fun mapToDto(movieList: List<Movie>): List<MovieDto> =
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

    fun mapToModel(movieList: List<MovieDto>): List<Movie> =
        movieList.map { dto ->
            Movie(
                id = dto.id,
                nameOriginal = dto.nameOriginal,
                description = dto.description,
                genres = dto.genres.map { genreDto ->
                    genreDto.genre
                },
                startYear = dto.startYear,
                countries = dto.countries.map { countryDto ->
                    countryDto.country
                },
                imageUrl = dto.imageUrl,
                ratingKinopoisk = dto.ratingKinopoisk
            )
        }

    fun mapDomainToUI(movieList: List<Movie>) =
        movieList.map { domain ->
            MovieUi(
                id = domain.id,
                nameOriginal = domain.nameOriginal,
                description = domain.description,
                filmYearAndCountry = domain.startYear + ", " + domain.countries.joinToString { countryDto -> countryDto },
                genres = domain.genres.map { genreDomain ->
                    genreDomain
                },
                ratingKinopoisk = domain.ratingKinopoisk,
                imageUrl = domain.imageUrl,
            )
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
