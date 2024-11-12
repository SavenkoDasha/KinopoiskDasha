package com.example.kinopoiskdasha.data

import android.content.Context.MODE_PRIVATE
import com.example.kinopoiskdasha.data.retrofit.Common
import com.example.kinopoiskdasha.ui.KinopoiskApp
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object Provider {
    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val dataSource: UserDataSource = UserDataSourceImpl(KinopoiskApp.applicationContext().getSharedPreferences("appSettings", MODE_PRIVATE))
    val dataSourceMovie: MovieDataSource = MovieDataSourceImpl(Common.retrofitService)

    val userRepository: UserRepository = UserRepositoryImpl(dataSource)
    val movieRepository: MovieRepository = MovieRepositoryImpl(dataSourceMovie)
}
