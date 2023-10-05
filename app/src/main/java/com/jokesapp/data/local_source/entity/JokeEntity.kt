package com.jokesapp.data.local_source.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JokeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val jokeText: String
)
