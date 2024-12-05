package com.example.kinopoiskdasha.di

import com.example.kinopoiskdasha.data.MovieRepository
import com.example.kinopoiskdasha.data.MovieRepositoryImpl
import com.example.kinopoiskdasha.data.UserRepository
import com.example.kinopoiskdasha.data.UserRepositoryImpl
import com.example.kinopoiskdasha.domain.User
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DomainModuleBind {

    @Singleton
    @Binds
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @Singleton
    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}

@Module
@InstallIn(SingletonComponent::class)
object DomainModuleProvider {

    @Singleton
    @Provides
    fun provideUserAdapter(moshi: Moshi) = moshi.adapter(User::class.java)
}
