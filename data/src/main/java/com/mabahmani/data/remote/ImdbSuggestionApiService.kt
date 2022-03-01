package com.mabahmani.data.remote

import com.mabahmani.data.vo.res.SuggestRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ImdbSuggestionApiService {

    @GET("{firstLetter}/{term}" + ".json")
    suspend fun suggestAll(
        @Path("firstLetter") firstLetter: String,
        @Path("term") term: String,
    ): Response<SuggestRes>

    @GET("titles/{firstLetter}/{term}" + ".json")
    suspend fun suggestTitles(
        @Path("firstLetter") firstLetter: String,
        @Path("term") term: String,
    ): Response<SuggestRes>

    @GET("names/{firstLetter}/{term}" + ".json")
    suspend fun suggestNames(
        @Path("firstLetter") firstLetter: String,
        @Path("term") term: String,
    ): Response<SuggestRes>

}