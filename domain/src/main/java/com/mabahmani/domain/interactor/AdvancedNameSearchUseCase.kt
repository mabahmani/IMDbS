package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.SearchRepository
import com.mabahmani.domain.vo.common.TitleId
import com.mabahmani.domain.vo.enum.Gender
import com.mabahmani.domain.vo.enum.NameGroup
import com.mabahmani.domain.vo.enum.NameSign
import com.mabahmani.domain.vo.enum.NameSort
import javax.inject.Inject

class AdvancedNameSearchUseCase @Inject constructor(private val searchRepository: SearchRepository){
    suspend operator fun invoke(
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
    ) = searchRepository.searchNames(
        bio, birthDate, birthMonthDay, birthPlace, deathDate, deathPlace, gender, group, name, role, sort, starSign, startPosition
        )
}