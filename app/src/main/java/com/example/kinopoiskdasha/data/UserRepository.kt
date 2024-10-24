package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicReference

interface UserRepository {
    suspend fun saveUser(user: User)
    suspend fun deleteUser()
    suspend fun fetchUser(): User?
    suspend fun getUser(): User?
}

class UserRepositoryImpl(private val source: UserDataSource) : UserRepository {
    private var cachedUser: AtomicReference<User?> = AtomicReference()

    override suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            val userData = User(user.email, user.password)
            source.updateUser(userData)
        }
    }

    override suspend fun deleteUser() {
        withContext(Dispatchers.IO) {
            source.deleteUser()
            cachedUser.set(null)
        }
    }

    override suspend fun fetchUser(): User? {
        return withContext(Dispatchers.IO) {
            cachedUser.get()
        }
    }

    override suspend fun getUser(): User? {
        return withContext(Dispatchers.IO) {
            val userData = source.getUser() ?: return@withContext null
            cachedUser.set(User(userData.email, userData.password))
            cachedUser.get()
        }
    }
}
