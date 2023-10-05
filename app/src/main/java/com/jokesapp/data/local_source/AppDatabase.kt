package com.jokesapp.data.local_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jokesapp.data.local_source.dao.JokeDao
import com.jokesapp.data.local_source.entity.JokeEntity

@Database(
    entities = [JokeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
}