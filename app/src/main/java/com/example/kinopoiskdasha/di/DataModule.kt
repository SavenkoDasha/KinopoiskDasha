package com.example.kinopoiskdasha.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import com.example.kinopoiskdasha.data.MovieDataSource
import com.example.kinopoiskdasha.data.MovieDataSourceImpl
import com.example.kinopoiskdasha.data.MovieLocalSource
import com.example.kinopoiskdasha.data.MovieLocalSourceImpl
import com.example.kinopoiskdasha.data.UserDataSource
import com.example.kinopoiskdasha.data.UserDataSourceImpl
import com.example.kinopoiskdasha.data.db.AppDatabase
import com.example.kinopoiskdasha.data.retrofit.RetrofitServices
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/"

@Module
@InstallIn(SingletonComponent::class)
interface DataModuleBinds {
    @Singleton
    @Binds
    fun bindUserDataSource(impl: UserDataSourceImpl): UserDataSource

    @Singleton
    @Binds
    fun bindMovieDataSource(impl: MovieDataSourceImpl): MovieDataSource

    @Singleton
    @Binds
    fun bindMovieLocalSource(impl: MovieLocalSourceImpl): MovieLocalSource
}

@Module
@InstallIn(SingletonComponent::class)
object DataModuleProvider {

    @Singleton
    @Provides
    fun provideMoviesDao(db: AppDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "movies_db"
    ).build()

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) = context
        .getSharedPreferences("appSettings", MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideRetrofitServices(client: Retrofit) = client.create(RetrofitServices::class.java)

    @Singleton
    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideLogging() = HttpLoggingInterceptor().also {
        it.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()
}
