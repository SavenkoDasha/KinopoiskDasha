package com.example.kinopoiskdasha.ui

import android.app.Application
import android.content.Context

class KinopoiskApp : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: KinopoiskApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}