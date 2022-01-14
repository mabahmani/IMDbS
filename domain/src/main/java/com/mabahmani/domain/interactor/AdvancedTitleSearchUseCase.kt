package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.enum.*
import javax.inject.Inject

class AdvancedTitleSearchUseCase @Inject constructor(private val searchRepository: SearchRepository){
    suspend operator fun invoke(
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
    ) = searchRepository.searchTitles(
            certificate, colors, companies, countries, genres, groups, keywords, languages, locations, plot, releaseDate, role, runtime, sort, startPosition, title, titleTypes, userRating
        )
}