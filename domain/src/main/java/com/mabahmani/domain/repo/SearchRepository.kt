package com.mabahmani.domain.repo

import androidx.paging.Pager
import com.mabahmani.domain.vo.Calender
import com.mabahmani.domain.vo.Suggestion
import com.mabahmani.domain.vo.common.*
import com.mabahmani.domain.vo.enum.*

interface SearchRepository {

    suspend fun getGenres(): Result<List<Genre>>

    suspend fun getKeywords(): Result<List<String>>

    suspend fun getEvents(): Result<List<Event>>

    suspend fun getCalender() : Result<List<Calender>>

    suspend fun searchNames(
        bio: String? = null,
        birthDate: String? = null,
        birthMonthDay: String? = null,
        birthPlace: List<String>? = null,
        deathDate: String? = null,
        deathPlace: List<String>? = null,
        gender: List<Gender>? = null,
        group: List<NameGroup>? = null,
        name: String? = null,
        role: TitleId? = null,
        sort: NameSort? = null,
        starSign: List<NameSign>? = null,
        startPosition: Int? = null
    ): Pager<Int, Name>

    suspend fun searchTitles(
        certificate: List<USCertificate>? = null,
        colors: List<Color>? = null,
        companies: List<Company>? = null,
        countries: List<String>? = null,
        genres: List<String>? = null,
        groups: List<TitleGroup>? = null,
        keywords: List<String>? = null,
        languages: List<String>? = null,
        locations: List<String>? = null,
        plot: String? = null,
        releaseDate: String? = null,
        role: NameId? = null,
        runtime: String? = null,
        sort: TitleSort? = null,
        startPosition: Int? = null,
        title: String? = null,
        titleTypes: List<TitleType>? = null,
        userRating: String? = null,
        ): Pager<Int, Title>

    suspend fun getSuggestion(
        suggestionType: SuggestionType = SuggestionType.ALL,
        term: String
    ): Result<List<Suggestion>>
}