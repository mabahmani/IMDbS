package com.mabahmani.data.repo

import com.mabahmani.data.ds.RemoteDataSource
import com.mabahmani.domain.repo.NameRepository
import com.mabahmani.domain.repo.NewsRepository
import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.*
import com.mabahmani.domain.vo.common.*
import com.mabahmani.domain.vo.enum.*
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource): SearchRepository {
    override suspend fun getGenres(): Result<List<Genre>> {
        val remoteResult = remoteDataSource.getGenres()

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toGenre() })
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getKeywords(): Result<List<String>> {
        val remoteResult = remoteDataSource.getKeywords()

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data)
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getEvents(): Result<List<Event>> {
        val remoteResult = remoteDataSource.getEvents()

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toEvent() })
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getCalender(): Result<List<Calender>> {
        val remoteResult = remoteDataSource.getTitlesCalender()

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toCalender() })
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun searchNames(
        bio: String?,
        birthDate: String?,
        birthMonthDay: String?,
        birthPlace: List<String>?,
        deathDate: String?,
        deathPlace: List<String>?,
        gender: List<Gender>?,
        group: List<NameGroup>?,
        name: String?,
        role: TitleId?,
        sort: NameSort?,
        starSign: List<NameSign>?,
        startPosition: Int?
    ): Result<List<Name>> {

        val birthPlaceJoinString = birthPlace?.joinToString()
        val deathPlaceJoinString = deathPlace?.joinToString()
        val genderJoinString = gender?.joinToString { it.name }
        val groupJoinString = group?.joinToString { it.name }
        val starJoinString = starSign?.joinToString { it.name }

        var sortString: String? = null

        when(sort){
            NameSort.STARMETER_ASC -> sortString = "starmeter,asc"
            NameSort.STARMETER_DESC -> sortString = "starmeter,desc"
            NameSort.ALPHA_ASC -> sortString = "alpha,asc"
            NameSort.ALPHA_DESC -> sortString = "alpha,desc"
            NameSort.BIRTH_DATE_ASC -> sortString = "birth_date,asc"
            NameSort.BIRTH_DATE_DESC -> sortString = "birth_date,desc"
            NameSort.DEATH_DATE_ASC -> sortString = "death_date,asc"
            NameSort.DEATH_DATE_DESC -> sortString = "death_date,desc"
        }


        val remoteResult = remoteDataSource.searchNames(
            bio,
            birthDate,
            birthMonthDay,
            birthPlaceJoinString,
            deathDate,
            deathPlaceJoinString,
            genderJoinString,
            groupJoinString,
            name,
            role?.value,
            sortString,
            starJoinString,
            startPosition.toString()
        )

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toName()})
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun searchTitles(
        certificate: List<USCertificate>?,
        colors: List<Color>?,
        companies: List<Company>?,
        countries: List<String>?,
        genres: List<String>?,
        groups: List<NameGroup>?,
        keywords: List<String>?,
        languages: List<String>?,
        locations: List<String>?,
        plot: String?,
        releaseDate: String?,
        role: NameId?,
        runtime: String?,
        sort: TitleSort?,
        startPosition: Int?,
        title: String?,
        titleTypes: List<TitleType>?,
        userRating: String?
    ): Result<List<Title>> {

        val certificateJoinString = certificate?.joinToString { it.name }
        val colorsJoinString = colors?.joinToString { it.name }
        val companiesJoinString = companies?.joinToString { it.name }
        val countriesJoinString = countries?.joinToString()
        val genresJoinString = countries?.joinToString()
        val groupsJoinString = groups?.joinToString { it.name }
        val keywordsJoinString = keywords?.joinToString()
        val languagesJoinString = languages?.joinToString()
        val locationsJoinString = locations?.joinToString()
        val titleTypesJoinString = titleTypes?.joinToString { it.name }

        var sortString: String? = null

        when (sort) {
            TitleSort.MOVIEMETER_ACS -> sortString = "moviemeter,acs"
            TitleSort.MOVIEMETER_DESC -> sortString = "moviemeter,desc"
            TitleSort.ALPHA_ASC -> sortString = "alpha,asc"
            TitleSort.ALPHA_DESC -> sortString = "alpha,desc"
            TitleSort.USER_RATING_ASC -> sortString = "user_rating,asc"
            TitleSort.USER_RATING_DESC -> sortString = "user_rating,desc"
            TitleSort.NUM_VOTES_ASC -> sortString = "num_votes,asc"
            TitleSort.NUM_VOTES_DESC -> sortString = "num_votes,desc"
            TitleSort.BOXOFFICE_GROSS_US_ASC -> sortString = "boxoffice_gross_us,asc"
            TitleSort.BOXOFFICE_GROSS_US_DESC -> sortString = "boxoffice_gross_us,desc"
            TitleSort.RUNTIME_ASC -> sortString = "runtime,asc"
            TitleSort.RUNTIME_DESC -> sortString = "runtime,desc"
            TitleSort.YEAR_ASC -> sortString = "year,asc"
            TitleSort.YEAR_DESC -> sortString = "year,desc"
            TitleSort.RELEASE_DATE_ASC -> sortString = "release_date,asc"
            TitleSort.RELEASE_DATE_DESC -> sortString = "release_date,desc"
        }


        val remoteResult = remoteDataSource.searchTitles(
            certificateJoinString,
            colorsJoinString,
            companiesJoinString,
            countriesJoinString,
            genresJoinString,
            groupsJoinString,
            keywordsJoinString,
            languagesJoinString,
            locationsJoinString,
            plot,
            releaseDate,
            role?.value,
            runtime,
            sortString,
            startPosition.toString(),
            title,
            titleTypesJoinString,
            userRating
        )

        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.map { it.toTitle()})
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

    override suspend fun getSuggestion(
        suggestionType: SuggestionType,
        term: String
    ): Result<List<Suggestion>> {
        val remoteResult =
        when(suggestionType){
            SuggestionType.ALL ->{
                 remoteDataSource.suggestAll(
                    term[0].toString(),
                    term
                )
            }

            SuggestionType.TITLE ->{
                remoteDataSource.suggestTitles(
                    term[0].toString(),
                    term
                )
            }

            SuggestionType.CELEB ->{
                remoteDataSource.suggestNames(
                    term[0].toString(),
                    term
                )
            }
        }


        return if (remoteResult.isSuccess) {
            try {
                Result.success(remoteResult.getOrNull()!!.data.toSuggestion())
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        } else
            Result.failure(remoteResult.exceptionOrNull() ?: java.lang.Exception())
    }

}