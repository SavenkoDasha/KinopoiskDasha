package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.ui.KinopoiskApp
import android.content.Context.MODE_PRIVATE
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Provider {
     val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val dataSource by lazy { UserDataSource(KinopoiskApp.applicationContext().getSharedPreferences("appSettings", MODE_PRIVATE)) }
}
