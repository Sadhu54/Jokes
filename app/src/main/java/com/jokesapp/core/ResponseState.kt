package com.jokesapp.core


/**
 * CREATED BY Ujwal
 */

// A sealed class representing the possible states of an API response
sealed class ResponseState<T>(val data: T? = null, val message: String? = null) {
    // Represents a successful response with optional data
    class Success<T>(data: T? = null) : ResponseState<T>(data)
    // Represents an error response with an optional error message
    class Error<T>(message: String? = null) : ResponseState<T>(message = message)
    // Represents a loading state while waiting for the response
    class Loading<T>() : ResponseState<T>()
}

// Extension function to map the success state of a ResponseState to a different type
fun <T, R> ResponseState<T>.mapSuccess(mapper: (T) -> R): ResponseState<R> {
    return when (this) {
        is ResponseState.Success -> ResponseState.Success(data?.let { mapper(it) })
        is ResponseState.Error -> ResponseState.Error(message)
        is ResponseState.Loading -> ResponseState.Loading()
    }
}
