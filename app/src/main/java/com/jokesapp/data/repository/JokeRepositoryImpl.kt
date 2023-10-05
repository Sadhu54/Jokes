package com.jokesapp.data.repository

import com.jokesapp.core.BaseRepo
import com.jokesapp.core.ResponseState
import com.jokesapp.core.mapSuccess
import com.jokesapp.data.local_source.dao.JokeDao
import com.jokesapp.data.local_source.entity.JokeEntity
import com.jokesapp.data.source.api.JokesApi
import com.jokesapp.domain.model.Jokes
import com.jokesapp.domain.repository.JokesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class JokeRepositoryImpl @Inject constructor(private val jokesApi: JokesApi, private val jokeDao: JokeDao):BaseRepo(), JokesRepository {
   // from network
    override suspend fun getJokes(): ResponseState<Jokes>{
        return  safeApiCall {
            jokesApi.getJokes()
        }.mapSuccess {dto->
            CoroutineScope(Dispatchers.IO).launch {
                jokeDao.insertJokeWithSizeLimit(JokeEntity(jokeText = dto.joke))
            }
            dto.toJokeObject()
        }
    }

    // from local
    override suspend fun getJokesFromDB(): List<Jokes> {
       return jokeDao.getAllJokes().map {
            Jokes(joke = it.jokeText)
        }
    }
}