package com.example.kinopoiskdasha.data

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.example.kinopoiskdasha.data.Provider.moshi
import com.example.kinopoiskdasha.data.dto.UserData
import com.squareup.moshi.JsonAdapter

private const val USER_KEY = "USER_KEY"

class UserDataSource(
    private val preferences: SharedPreferences,
    private val adapter: JsonAdapter<UserData> = moshi.adapter(UserData::class.java)
) {

    @SuppressLint("ApplySharedPref")
    fun updateUser(user: UserData) {

        val json = adapter.toJson(user)
        preferences.edit()
            .putString(USER_KEY, json)
            .commit()
    }

    @SuppressLint("ApplySharedPref")
    fun deleteUser() {
        preferences.edit()
            .remove(USER_KEY)
            .commit()
    }
}