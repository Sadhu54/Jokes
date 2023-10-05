package com.jokesapp.presentation.jokes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jokesapp.core.ResponseState
import com.jokesapp.domain.model.Jokes
import com.jokesapp.domain.repository.JokesRepository
import com.jokesapp.domain.use_case.JokeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeViewmodel @Inject constructor(private val jokeUseCase: JokeUseCase ):ViewModel(){
    private var _getJoke= MutableStateFlow<ResponseState<Jokes>?>(null)
    var getJoke:StateFlow<ResponseState<Jokes>?> = _getJoke


    private var _getJokeFromDB= MutableStateFlow<List<Jokes>?>(null)
    var getJokeFromDB:StateFlow<List<Jokes>?> = _getJokeFromDB

    fun getJoke(){
        viewModelScope.launch (Dispatchers.IO){
            jokeUseCase.getJokes().collect{
                _getJoke.value=it
            }

        }
    }

    fun getJokeFromDB(){
        viewModelScope.launch (Dispatchers.IO){
            jokeUseCase.getJokesFromDb().collect{
                _getJokeFromDB.value=it
            }

        }
    }

}