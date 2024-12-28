package com.example.kinopoiskdasha.data

import com.example.kinopoiskdasha.domain.Result
import com.example.kinopoiskdasha.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

interface UserRepository {
    suspend fun saveUser(user: User)
    suspend fun deleteUser()
    suspend fun getUser(): Result<User, Throwable>
}

class UserRepositoryImpl @Inject constructor(
    private val source: UserDataSource,
) : UserRepository {
    private var cachedUser: AtomicReference<User?> = AtomicReference()

    override suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            val userData = User(user.email, user.password, user.lastSuccessfulLogin)
            source.updateUser(userData)
        }
    }

    override suspend fun deleteUser() {
        withContext(Dispatchers.IO) {
            source.deleteUser()
            cachedUser.set(null)
        }
    }

    override suspend fun getUser(): Result<User, Throwable> {
        return try {
            withContext(Dispatchers.IO) {
                val userData = source.getUser() ?: throw Throwable("User not found")
                cachedUser.set(userData)
                Result.Success(userData)
            }
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }
}
