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
    val countries: String? = null,
    @Json(name = "genres")
    val genres: String? = null,
    @Json(name = "startYear")
    val startYear: String? = null,
    @Json(name = "endYear")
    val endYear: String? = null,
    @Json(name = "ratingKinopoisk")
    val ratingKinopoisk: Double? = null,
    @Json(name = "imageUrl")
    val imageUrl: String? = null,
)
