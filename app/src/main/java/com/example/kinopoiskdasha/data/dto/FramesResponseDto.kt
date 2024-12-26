package com.example.kinopoiskdasha.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class FramesResponseDto (
    @Json(name = "total")
    val total: Int,
    @Json(name = "totalPages")
    val totalPages: Int,
    @Json(name = "items")
    val items: List<ImagesResponseDto>,
)

@JsonClass(generateAdapter = true)
data class ImagesResponseDto (
    @Json(name = "imageUrl")
    val imageUrl: String,
    @Json(name = "previewUrl")
    val previewUrl: String,
)
