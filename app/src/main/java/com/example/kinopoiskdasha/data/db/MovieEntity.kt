package com.example.kinopoiskdasha.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class MovieEntity (
    @PrimaryKey val id: Int,

    @ColumnInfo val name: String,

    @ColumnInfo val description: String,

    @ColumnInfo val countries: List<String>,

    @ColumnInfo val genres: List<String>,

    @ColumnInfo(name = "start_year") val startYear: String,

    @ColumnInfo(name = "rating_kinopoisk") val ratingKinopoisk: Double,

    @ColumnInfo(name = "image_url") val imageUrl: String,
)
