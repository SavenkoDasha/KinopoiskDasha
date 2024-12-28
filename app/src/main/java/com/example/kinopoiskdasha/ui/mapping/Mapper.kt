package com.example.kinopoiskdasha.ui.mapping

import android.content.Context
import com.example.kinopoiskdasha.R
import com.example.kinopoiskdasha.domain.Frame
import com.example.kinopoiskdasha.domain.Movie
import com.example.kinopoiskdasha.ui.model.ImageItem
import com.example.kinopoiskdasha.ui.model.MovieDetailUi
import com.example.kinopoiskdasha.ui.model.MovieUi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.temporal.ChronoUnit
import java.time.LocalDateTime as JavaLocalDateTime
import javax.inject.Inject

fun Frame.mapToImageItem() = ImageItem(
    imageUrl = imageUrl ?: previewUrl,
)

fun Movie.mapToUI() = MovieUi(
    id = id,
    nameOriginal = nameOriginal ?: "empty",
    description = description ?: "empty",
    movieYearAndCountry = "$startYear, ${countries.joinToString()}",
    genres = genres.joinToString(),
    ratingKinopoisk = ratingKinopoisk.toString(),
    imageUrl = imageUrl ?: "empty",
)

fun Movie.mapToDetailUi() = MovieDetailUi(
    id = id,
    nameOriginal = nameOriginal ?: "empty",
    description = genres.joinToString(),
    movieYearAndCountry = "$startYear, ${countries.joinToString()}",
    genres = genres.joinToString(),
    ratingKinopoisk = ratingKinopoisk.toString(),
    coverUrl = coverUrl ?: imageUrl ?: "empty",
)

class DateTimeMapper @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun map(dateTime: LocalDateTime): String {
        val current = JavaLocalDateTime.now()
        val old = dateTime.toJavaLocalDateTime()

        val days = ChronoUnit.DAYS.between(old, current).toInt()
        val hours = ChronoUnit.HOURS.between(old, current).toInt()
        val minutes = ChronoUnit.MINUTES.between(old, current).toInt()

        return when {
            days > 0 -> mapToDays(days)
            hours > 0 -> mapToHours(hours)
            else -> mapToMinutes(minutes)
        }
    }

    private fun mapToDays(days: Int): String =
        context.resources.getQuantityString(R.plurals.days, days, days)


    private fun mapToMinutes(minutes: Int): String =
        when {
            minutes < 1 -> "Последний вход был недавно"
            minutes < 60 -> context.resources.getQuantityString(R.plurals.minutes, minutes, minutes)
            else -> ""
        }

    private fun mapToHours(hours: Int): String =
        when {
            hours < 24 -> context.resources.getQuantityString(R.plurals.minutes, hours, hours)
            else -> ""
        }
}
