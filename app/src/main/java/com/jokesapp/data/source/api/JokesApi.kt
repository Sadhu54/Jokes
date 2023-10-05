package com.jokesapp.data.source.api

import com.jokesapp.data.source.dto.JokesDTO
import retrofit2.Response
import retrofit2.http.GET

interface JokesApi {

    @GET ("api?format=json")
    suspend fun getJokes(): Response<JokesDTO>
}