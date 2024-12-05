package com.example.kinopoiskdasha.ui.model

data class YearItem(
    val year: String
): ListItem

fun String.toYearItem() = YearItem(year = this)
