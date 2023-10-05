package com.jokesapp.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseRepo() {

    // A generic function to make a safe API call and handle different scenarios
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>?): ResponseState<T> {
        return withContext(Dispatchers.IO) {
            try {
                // Indicate that the API call is in progress
                ResponseState.Loading<T>()

                // Call the API using the provided lambda function
                val response: Response<T> = apiToBeCalled()!!

                // Check if the API call was successful
                if (response.isSuccessful) {
                    // Return the successful response with data
                    ResponseState.Success(data = response.body()!!)
                } else {
                    // Return an error response with the message from the API
                    ResponseState.Error(message = response.message())
                }

            } catch (e: HttpException) {
                ResponseState.Error(message = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                ResponseState.Error(message = "Please check your internet")
            } catch (e: Exception) {
                ResponseState.Error(message = "Something went wrong")
            } catch (e: UnknownHostException) {
                ResponseState.Error(message = "Something went wrong")
            } catch (e: SocketTimeoutException) {
                ResponseState.Error(message = "Something went wrong")
            }
        }
    }

}
