package com.example.kinopoiskdasha.data.dto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    @Json(name = "kinopoiskId")
    val id: Int,
    @Json(name = "nameRu")
    val nameRussian: String? = null,
    @Json(name = "nameEn")
    val nameEnglish: String? = null,
    @Json(name = "nameOriginal")
    val nameOriginal: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "countries")
    val countries: List<CountryDto> = emptyList(),
    @Json(name = "genres")
    val genres: List<GenreDto> = emptyList(),
    @Json(name = "year")
    val startYear: String? = null,
    @Json(name = "ratingKinopoisk")
    val ratingKinopoisk: Double? = null,
    @Json(name = "posterUrlPreview")
    val imageUrl: String? = null,
    @Json(name = "coverUrl")
    val coverUrl: String? = null,
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
