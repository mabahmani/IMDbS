package com.mabahmani.domain.interactor

import com.mabahmani.domain.repo.*
import com.mabahmani.domain.vo.common.EventId
import com.mabahmani.domain.vo.common.NameId
import com.mabahmani.domain.vo.enum.SuggestionType
import javax.inject.Inject

class GetNameDetailsUseCase @Inject constructor(private val nameRepository: NameRepository){
    suspend operator fun invoke(nameId: NameId) = nameRepository.getNameDetails(nameId)
}