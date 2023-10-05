package com.jokesapp.domain.repository

import com.jokesapp.core.ResponseState
import com.jokesapp.domain.model.Jokes

interface JokesRepository {
    suspend fun getJokes():ResponseState<Jokes>

    suspend fun getJokesFromDB(): List<Jokes>
}