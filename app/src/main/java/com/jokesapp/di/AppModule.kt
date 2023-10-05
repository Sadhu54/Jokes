package com.jokesapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jokesapp.data.local_source.AppDatabase
import com.jokesapp.data.local_source.dao.JokeDao
import com.jokesapp.data.repository.JokeRepositoryImpl
import com.jokesapp.data.source.api.JokesApi
import com.jokesapp.domain.repository.JokesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.sql.Time
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getOkHttpLogging():OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .retryOnConnectionFailure(true)
            .connectTimeout(30,TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return  Retrofit.Builder()
            .baseUrl("https://geek-jokes.sameerkumar.website/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getJokesAPI(retrofit: Retrofit):JokesApi{
        return  retrofit.create(JokesApi::class.java)
    }

    @Provides
    @Singleton
    fun getJokeRepository(jokesApi: JokesApi,jokeDao: JokeDao):JokesRepository{
        return  JokeRepositoryImpl(jokesApi,jokeDao)
    }

    @Provides
    @Singleton
    fun getAppDatabase(application: Application):AppDatabase{
        return Room.databaseBuilder(application,AppDatabase::class.java,"jokes_db").build()
    }

    @Provides
    @Singleton
    fun getJokeDao(appDatabase: AppDatabase):JokeDao{
        return appDatabase.jokeDao()
    }
}