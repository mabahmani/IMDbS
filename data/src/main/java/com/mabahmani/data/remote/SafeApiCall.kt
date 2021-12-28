package com.mabahmani.data.remote

import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {

    return try {
        val response =  apiCall.invoke()
        if (response.isSuccessful){
            Result.success(response.body()!!)
        }
        else{
            Result.failure(HttpException(response))
        }
    }
    catch (throwable: Throwable){
        Result.failure(throwable)
    }
}