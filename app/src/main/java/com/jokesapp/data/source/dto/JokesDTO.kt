package com.jokesapp.data.source.dto

import com.jokesapp.domain.model.Jokes

data class JokesDTO(
    val joke: String
){
    fun toJokeObject():Jokes{
        return  Jokes(joke = joke)
    }
}