package com.example.kinopoiskdasha.data.dto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    @Json(name = "nameOriginal")
    val nameOriginal: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "countries")
    val countries: List<CountryDto> = emptyList(),
    @Json(name = "genres")
    val genres: List<GenreDto> = emptyList(),
    @Json(name = "startYear")
    val startYear: String? = null,
    @Json(name = "endYear")
    val endYear: String? = null,
    @Json(name = "ratingKinopoisk")
    val ratingKinopoisk: Double? = null,
    @Json(name = "imageUrl")
    val imageUrl: String? = null,
)

@JsonClass(generateAdapter = true)
data class CountryDto(
    @Json(name = "country")
    val country: String
)

@JsonClass(generateAdapter = true)
data class GenreDto(
    @Json(name = "genre")
    val genre: String
)
