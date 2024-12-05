package com.example.kinopoiskdasha.ui

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KinopoiskApp : Application() {

    companion object {
        private var instance: KinopoiskApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}
