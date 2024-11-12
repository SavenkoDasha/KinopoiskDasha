package com.example.kinopoiskdasha.data

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.example.kinopoiskdasha.data.Provider.moshi
import com.example.kinopoiskdasha.domain.User
import com.squareup.moshi.JsonAdapter

private const val USER_KEY = "USER_KEY"

interface UserDataSource {
    suspend fun getUser(): User?
    suspend fun updateUser(user: User)
    suspend fun deleteUser()
}

class UserDataSourceImpl(
    private val preferences: SharedPreferences,
    private val adapter: JsonAdapter<User> = moshi.adapter(User::class.java)
) : UserDataSource {
    @SuppressLint("ApplySharedPref")
    override suspend fun getUser() =
        try {
            val res = preferences.getString(USER_KEY, "")
            adapter.fromJson(res.orEmpty())
        } catch (cause: Throwable) {
            null
        }

    @SuppressLint("ApplySharedPref")
    override suspend fun updateUser(user: User) {
        val json = adapter.toJson(user)
        preferences.edit()
            .putString(USER_KEY, json)
            .commit()
    }

    @SuppressLint("ApplySharedPref")
    override suspend fun deleteUser() {
        preferences.edit()
            .remove(USER_KEY)
            .commit()
    }
}
