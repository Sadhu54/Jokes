package com.jokesapp.domain.use_case

import com.jokesapp.core.ResponseState
import com.jokesapp.domain.repository.JokesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JokeUseCase @Inject constructor(private val jokesRepository: JokesRepository) {

    suspend fun getJokes()= flow {
        emit(ResponseState.Loading())
        emit(jokesRepository.getJokes())
    }

    suspend fun getJokesFromDb()= flow {
        emit(jokesRepository.getJokesFromDB())
    }
}