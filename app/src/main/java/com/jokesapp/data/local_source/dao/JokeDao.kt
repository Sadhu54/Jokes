package com.jokesapp.data.local_source.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jokesapp.data.local_source.entity.JokeEntity

@Dao
interface JokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: JokeEntity)

    // Add a custom method to enforce the list size limit
    @Transaction
    suspend fun insertJokeWithSizeLimit(joke: JokeEntity) {
        if (getJokeCount() < 10) {
            insertJoke(joke)
        } else {
            // Remove the oldest joke and then insert the new one
            val oldestJoke = getOldestJoke()
            oldestJoke?.let {
                deleteJoke(oldestJoke)
                insertJoke(joke)
            }
        }
    }

    // Helper methods to get the count and oldest joke
    @Query("SELECT COUNT(*) FROM jokeentity")
    suspend fun getJokeCount(): Int

    @Query("SELECT * FROM jokeentity ORDER BY id ASC LIMIT 1")
    suspend fun getOldestJoke(): JokeEntity?

    @Delete
    suspend fun deleteJoke(joke: JokeEntity)


    @Query("SELECT * FROM jokeentity")
    suspend fun getAllJokes(): List<JokeEntity>

}
