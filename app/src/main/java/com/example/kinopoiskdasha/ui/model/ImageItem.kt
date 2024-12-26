package com.example.kinopoiskdasha.ui.model

data class ImageItem(
    val imageUrl : String?,
): DetailListItem

data class MockImageItem(
    val text: String = "Нет кадров"
): DetailListItem
