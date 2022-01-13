package com.mabahmani.data.remote

import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T> safeApiCall(
    okHttpClient: OkHttpClient? = null,
    apiCall: suspend () -> Response<T>
): Result<T> {

    return try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            try {
                okHttpClient?.cache?.delete()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            Result.failure(HttpException(response))
        }
    } catch (throwable: Throwable) {
        try {
            okHttpClient?.cache?.delete()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        Result.failure(throwable)
    }
}