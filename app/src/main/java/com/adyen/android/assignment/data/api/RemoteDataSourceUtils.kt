package com.adyen.android.assignment.data.api

import retrofit2.HttpException
import retrofit2.Response

/**
 * Handle a network request so that a valid object is returned
 * and warn for common errors
 */
suspend fun <T, R> request(
    sourceCall: suspend () -> Response<T>,
    mapper: (response: T) -> R,
): R {
    val response = sourceCall()
    if (!response.isSuccessful) throw HttpException(response)
    val responseEntity = response.body()
        ?: throw EmptyBodyException()
    return mapper.invoke(responseEntity)
}